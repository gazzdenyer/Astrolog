import React, { useContext } from 'react';
import { ObservationContext } from '../context/ObservationContext';
import '../styles/StarChartDisplay.css';

const StarChartDisplay = () => {
  const { currentObservation, error } = useContext(ObservationContext);

  if (error) {
    return (
      <div className="star-chart-display-container">
        <div className="error-message">
          <h3>Error</h3>
          <p>{error}</p>
        </div>
      </div>
    );
  }

  if (!currentObservation) {
    return (
      <div className="star-chart-display-container">
        <div className="placeholder">
          <p>Generate a star chart to see it displayed here</p>
        </div>
      </div>
    );
  }

  return (
    <div className="star-chart-display-container">
      <div className="star-chart-details">
        <h2>Star Chart Results</h2>

        <div className="observation-info">
          <div className="info-row">
            <span className="label">Location:</span>
            <span className="value">{currentObservation.location}</span>
          </div>
          <div className="info-row">
            <span className="label">Coordinates:</span>
            <span className="value">
              {currentObservation.latitude.toFixed(4)}°, {currentObservation.longitude.toFixed(4)}°
            </span>
          </div>
          <div className="info-row">
            <span className="label">Date/Time:</span>
            <span className="value">{new Date(currentObservation.observationDateTime).toLocaleString()}</span>
          </div>
          <div className="info-row">
            <span className="label">Target:</span>
            <span className="value">{currentObservation.target}</span>
          </div>
          {currentObservation.notes && (
            <div className="info-row">
              <span className="label">Notes:</span>
              <span className="value">{currentObservation.notes}</span>
            </div>
          )}
        </div>

        <div className="chart-image-container">
          {currentObservation.chartUrl ? (
            <>
              <a
                href={currentObservation.chartUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="chart-link"
              >
                View Full Star Chart
              </a>
              <img
                src={currentObservation.chartUrl}
                alt="Star Chart"
                className="chart-image"
                onError={(e) => {
                  e.target.style.display = 'none';
                  console.error('Failed to load chart image');
                }}
              />
            </>
          ) : (
            <p className="no-image">Chart URL not available</p>
          )}
        </div>

        {currentObservation.localImagePath && (
          <div className="storage-info">
            <p>✓ Image stored locally: {currentObservation.localImagePath}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default StarChartDisplay;

