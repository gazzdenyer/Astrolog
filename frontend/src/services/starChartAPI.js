import axios from 'axios';

const API_BASE_URL = '/api/star-charts';

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add response interceptor for error handling
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API error:', error);
    return Promise.reject(error);
  }
);

export const starChartAPI = {
  /**
   * Generate a new star chart
   */
  generateStarChart: (data) => {
    return axiosInstance.post('/generate', data);
  },

  /**
   * Get all observations
   */
  getAllObservations: () => {
    return axiosInstance.get('/observations');
  },

  /**
   * Get observation by ID
   */
  getObservationById: (id) => {
    return axiosInstance.get(`/observations/${id}`);
  },

  /**
   * Get observations by location
   */
  getObservationsByLocation: (location) => {
    return axiosInstance.get(`/observations/location/${location}`);
  },

  /**
   * Get observations by target
   */
  getObservationsByTarget: (target) => {
    return axiosInstance.get(`/observations/target/${target}`);
  },

  /**
   * Update observation
   */
  updateObservation: (id, data) => {
    return axiosInstance.put(`/observations/${id}`, data);
  },

  /**
   * Delete observation
   */
  deleteObservation: (id) => {
    return axiosInstance.delete(`/observations/${id}`);
  },

  /**
   * Check API health
   */
  checkHealth: () => {
    return axiosInstance.get('/health');
  },
};

export default starChartAPI;

