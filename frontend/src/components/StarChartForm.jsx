import React, { useState, useContext } from 'react';
import starChartAPI from '../services/starChartAPI';
import { ObservationContext } from '../context/ObservationContext';
import '../styles/StarChartForm.css';

const StarChartForm = () => {
  const { addObservation, setLoading, setErrorMessage, clearError } = useContext(ObservationContext);

  const [formData, setFormData] = useState({
    location: '',
    latitude: '',
    longitude: '',
    observationDateTime: '',
    target: '',
    notes: '',
    downloadImage: false,
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
    clearError();
  };

  const validateForm = () => {
    if (!formData.location.trim()) {
      setErrorMessage('Location is required');
      return false;
    }
    if (!formData.latitude || isNaN(parseFloat(formData.latitude))) {
      setErrorMessage('Valid latitude is required (-90 to 90)');
      return false;
    }
    if (!formData.longitude || isNaN(parseFloat(formData.longitude))) {
      setErrorMessage('Valid longitude is required (-180 to 180)');
      return false;
    }
    if (!formData.observationDateTime) {
      setErrorMessage('Observation date/time is required');
      return false;
    }
    if (!formData.target.trim()) {
      setErrorMessage('Target is required');
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    clearError();
    setSuccessMessage('');

    if (!validateForm()) {
      return;
    }

    setIsSubmitting(true);
    setLoading(true);

    try {
      const requestData = {
        location: formData.location,
        latitude: parseFloat(formData.latitude),
        longitude: parseFloat(formData.longitude),
        observationDateTime: formData.observationDateTime,
        target: formData.target,
        notes: formData.notes,
        downloadImage: formData.downloadImage,
      };

      const response = await starChartAPI.generateStarChart(requestData);

      addObservation(response.data);
      setSuccessMessage('Star chart generated successfully!');

      // Reset form
      setFormData({
        location: '',
        latitude: '',
        longitude: '',
        observationDateTime: '',
        target: '',
        notes: '',
        downloadImage: false,
      });

      // Clear success message after 3 seconds
      setTimeout(() => setSuccessMessage(''), 3000);
    } catch (error) {
      console.error('Error generating star chart:', error);
      console.error('Error response:', error.response?.data);
      const errorMessage = 
        error.response?.data?.message || 
        error.message ||
        'Failed to generate star chart. Please check your input and try again.';
      setErrorMessage(errorMessage);
    } finally {
      setIsSubmitting(false);
      setLoading(false);
    }
  };

  return (
    <div className="star-chart-form-container">
      <h2>Generate Star Chart</h2>

      {successMessage && (
        <div className="success-message">{successMessage}</div>
      )}

      <form onSubmit={handleSubmit} className="star-chart-form">
        <div className="form-group">
          <label htmlFor="location">Location Name *</label>
          <input
            type="text"
            id="location"
            name="location"
            value={formData.location}
            onChange={handleInputChange}
            placeholder="e.g., New York, London"
            required
          />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label htmlFor="latitude">Latitude (-90 to 90) *</label>
            <input
              type="number"
              id="latitude"
              name="latitude"
              value={formData.latitude}
              onChange={handleInputChange}
              step="0.0001"
              min="-90"
              max="90"
              placeholder="e.g., 40.7128"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="longitude">Longitude (-180 to 180) *</label>
            <input
              type="number"
              id="longitude"
              name="longitude"
              value={formData.longitude}
              onChange={handleInputChange}
              step="0.0001"
              min="-180"
              max="180"
              placeholder="e.g., -74.0060"
              required
            />
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="observationDateTime">Observation Date/Time * (YYYY-MM-DD HH:mm)</label>
          <input
            type="datetime-local"
            id="observationDateTime"
            name="observationDateTime"
            value={formData.observationDateTime}
            onChange={handleInputChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="target">Target (Constellation, RA/Dec, etc.) *</label>
          <input
            type="text"
            id="target"
            name="target"
            value={formData.target}
            onChange={handleInputChange}
            placeholder="e.g., Orion, 5h 55m 10.3s +7 deg 24 min 25 sec"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="notes">Notes (Optional)</label>
          <textarea
            id="notes"
            name="notes"
            value={formData.notes}
            onChange={handleInputChange}
            placeholder="Add any additional notes about this observation..."
            rows="3"
          />
        </div>

        <div className="form-group checkbox">
          <input
            type="checkbox"
            id="downloadImage"
            name="downloadImage"
            checked={formData.downloadImage}
            onChange={handleInputChange}
          />
          <label htmlFor="downloadImage">Download and store chart image locally</label>
        </div>

        <button
          type="submit"
          className="submit-button"
          disabled={isSubmitting}
        >
          {isSubmitting ? 'Generating...' : 'Generate Star Chart'}
        </button>
      </form>
    </div>
  );
};

export default StarChartForm;

