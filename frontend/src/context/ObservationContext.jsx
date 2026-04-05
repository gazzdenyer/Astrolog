import React, { createContext, useState, useCallback } from 'react';

export const ObservationContext = createContext();

export const ObservationProvider = ({ children }) => {
  const [observations, setObservations] = useState([]);
  const [currentObservation, setCurrentObservation] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const addObservation = useCallback((observation) => {
    setObservations((prev) => [observation, ...prev]);
    setCurrentObservation(observation);
  }, []);

  const updateObservations = useCallback((newObservations) => {
    setObservations(newObservations);
  }, []);

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  const setErrorMessage = useCallback((message) => {
    setError(message);
  }, []);

  const value = {
    observations,
    currentObservation,
    loading,
    error,
    addObservation,
    updateObservations,
    setCurrentObservation,
    setLoading,
    clearError,
    setErrorMessage,
  };

  return (
    <ObservationContext.Provider value={value}>
      {children}
    </ObservationContext.Provider>
  );
};

