import React, { useState, useContext, useEffect } from 'react';
import starChartAPI from '../services/starChartAPI';
import { ObservationContext } from '../context/ObservationContext';
import '../styles/ObservationLog.css';

const ObservationLog = () => {
  const { observations, updateObservations, setCurrentObservation, setLoading, setErrorMessage } = useContext(ObservationContext);
  const [filteredObservations, setFilteredObservations] = useState([]);
  const [filterType, setFilterType] = useState('all');
  const [filterValue, setFilterValue] = useState('');
  const [deleting, setDeleting] = useState(null);

  useEffect(() => {
    fetchAllObservations();
  }, []);

  const fetchAllObservations = async () => {
    setLoading(true);
    try {
      const response = await starChartAPI.getAllObservations();
      updateObservations(response.data);
      setFilteredObservations(response.data);
    } catch (error) {
      console.error('Error fetching observations:', error);
      setErrorMessage('Failed to fetch observations');
    } finally {
      setLoading(false);
    }
  };

  const handleFilterChange = (type) => {
    setFilterType(type);
    setFilterValue('');
    if (type === 'all') {
      setFilteredObservations(observations);
    }
  };

  const handleFilterSearch = async (e) => {
    const value = e.target.value;
    setFilterValue(value);

    if (value.trim() === '') {
      setFilteredObservations(observations);
      return;
    }

    try {
      let response;
      if (filterType === 'location') {
        response = await starChartAPI.getObservationsByLocation(value);
      } else if (filterType === 'target') {
        response = await starChartAPI.getObservationsByTarget(value);
      }
      setFilteredObservations(response.data);
    } catch (error) {
      console.error('Error filtering observations:', error);
    }
  };

  const handleSelectObservation = (observation) => {
    setCurrentObservation(observation);
  };

  const handleDeleteObservation = async (id) => {
    if (window.confirm('Are you sure you want to delete this observation?')) {
      setDeleting(id);
      try {
        await starChartAPI.deleteObservation(id);
        const updated = observations.filter(obs => obs.id !== id);
        updateObservations(updated);
        setFilteredObservations(updated);
        setErrorMessage('Observation deleted successfully');
      } catch (error) {
        console.error('Error deleting observation:', error);
        setErrorMessage('Failed to delete observation');
      } finally {
        setDeleting(null);
      }
    }
  };

  return (
    <div className="observation-log-container">
      <h2>Observation Log</h2>

      <div className="filter-controls">
        <div className="filter-buttons">
          <button
            className={`filter-btn ${filterType === 'all' ? 'active' : ''}`}
            onClick={() => handleFilterChange('all')}
          >
            All
          </button>
          <button
            className={`filter-btn ${filterType === 'location' ? 'active' : ''}`}
            onClick={() => handleFilterChange('location')}
          >
            By Location
          </button>
          <button
            className={`filter-btn ${filterType === 'target' ? 'active' : ''}`}
            onClick={() => handleFilterChange('target')}
          >
            By Target
          </button>
        </div>

        {filterType !== 'all' && (
          <input
            type="text"
            placeholder={`Search by ${filterType}...`}
            value={filterValue}
            onChange={handleFilterSearch}
            className="filter-input"
          />
        )}
      </div>

      <div className="observations-list">
        {filteredObservations.length === 0 ? (
          <p className="no-observations">No observations found</p>
        ) : (
          <table className="observations-table">
            <thead>
              <tr>
                <th>Location</th>
                <th>Target</th>
                <th>Date/Time</th>
                <th>Coordinates</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredObservations.map((obs) => (
                <tr key={obs.id} onClick={() => handleSelectObservation(obs)} className="observation-row">
                  <td>{obs.location}</td>
                  <td>{obs.target}</td>
                  <td>{new Date(obs.observationDateTime).toLocaleString()}</td>
                  <td className="coordinates">
                    {parseFloat(obs.latitude).toFixed(4)}°, {parseFloat(obs.longitude).toFixed(4)}°
                  </td>
                  <td className="actions" onClick={(e) => e.stopPropagation()}>
                    <button
                      className="delete-btn"
                      onClick={() => handleDeleteObservation(obs.id)}
                      disabled={deleting === obs.id}
                      title="Delete observation"
                    >
                      {deleting === obs.id ? '...' : '✕'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default ObservationLog;

