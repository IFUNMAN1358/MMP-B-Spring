<script>
import axios from "@/axios";
import { getAccessToken } from '@/js/token';

export default {
  name: "RefreshComponent",
  methods: {
    async testEndpoint() {
      try {
        const accessToken = getAccessToken();

        const response = await axios.get("/api/test", {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<template>
  <div>
    <button @click="testEndpoint">Test</button>
  </div>
</template>