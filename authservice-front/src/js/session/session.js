import axios from '@/axios';
import UAParser from 'ua-parser-js';
import { getJwtTokens, saveJwtTokens } from "@/js/token";
import { getIpData } from "@/js/ip";


async function createSessionData(userId) {
  try {
    const parser = new UAParser();
    const deviceInfo = parser.getResult();
    const jwtTokens = getJwtTokens();
    const ipData = await getIpData();

    if (!userId) {
      throw new Error('Missing user id');
    }
    if (!jwtTokens.accessToken || !jwtTokens.refreshToken) {
      throw new Error('Missing JWT tokens');
    }
    if (!ipData || !ipData.city || !ipData.country_name) {
      throw new Error('Missing IP data');
    }

    return {
      userId: userId,
      accessToken: jwtTokens.accessToken,
      refreshToken: jwtTokens.refreshToken,
      device: `${deviceInfo.browser.name} ${deviceInfo.browser.major}`,
      service: `${process.env.VUE_APP_SERVICE_NAME} ${process.env.VUE_APP_SERVICE_VERSION}`,
      os: `${deviceInfo.os.name} ${deviceInfo.os.version}`,
      location: `${ipData.city}, ${ipData.country_name}`
    };
  } catch (error) {
    console.error('Error creating session data:', error);
    return null;
  }
}

export async function createSession(userId) {

    const sessionData = await createSessionData(userId);

    if (!sessionData) {
      throw new Error('Missing session data');
    }

    try {
        const response = await axios.post('/api/session', sessionData);
        return response.data;
    } catch (error) {
        console.error('Failed to create session:', error);
        throw error;
    }
}

export async function updateSession(refreshToken) {
    try {
        const response = await axios.post("/api/session/update", {
            refreshToken: refreshToken
        });
        const tokens = response.data;
        if (tokens.accessToken && tokens.refreshToken) {
            saveJwtTokens(tokens.accessToken, tokens.refreshToken);
        } else {
            throw new Error("Invalid tokens received from the server");
        }
    } catch (error) {
        console.error(error);
        throw error;
    }
}