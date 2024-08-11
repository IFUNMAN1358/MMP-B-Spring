import SockJS from 'sockjs-client';

const backendBaseUrl = process.env.VUE_APP_BACK_BASE_URL;
const socket = new SockJS(`${backendBaseUrl}/ws`);

export default socket;