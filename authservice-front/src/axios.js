import axios from 'axios';
import { deleteJwtTokens, getAccessToken, getRefreshToken } from '@/js/token';
import { updateSession } from '@/js/session/session';

axios.defaults.baseURL = process.env.VUE_APP_BACK_BASE_URL

axios.interceptors.response.use(
  async response => {
    return response;
  },
  async error => {
    const originalRequest = error.config;

    if (error.response.status === 403 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = getRefreshToken();
        if (refreshToken) {
          await updateSession(refreshToken);
        }
        const newAccessToken = getAccessToken();
        if (newAccessToken) {
          originalRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
          return axios(originalRequest);
        }
      } catch (error) {
        await deleteJwtTokens();
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);

export default axios;