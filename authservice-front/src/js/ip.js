import axios from "axios";

export async function getIpData() {
  try {
    const response = await axios.get('https://api.codetabs.com/v1/proxy?quest=https://api.ipify.org?format=json');
    const detailsResponse = await axios.get(`https://ipapi.co/${response.data.ip}/json/`);
    return detailsResponse.data;
  } catch (error) {
    console.error('Error getting IP data:', error);
    return null;
  }
}
