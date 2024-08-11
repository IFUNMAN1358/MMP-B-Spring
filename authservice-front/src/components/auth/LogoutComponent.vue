<script>
import axios from '@/axios';
import { getAccessToken } from "@/js/token";
import { format } from 'date-fns';

export default {
  name: "LogoutComponent",
  data() {
    return {
      sessions: []
    }
  },
  methods: {
    async getSessions() {
      try {
        const accessToken = getAccessToken();
        const response = await axios.get('/api/session', {
          params: {
            service: `${process.env.VUE_APP_SERVICE_NAME} ${process.env.VUE_APP_SERVICE_VERSION}`
          },
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        this.sessions = response.data;
      } catch (error) {
        console.log(error);
      }
    },
    async deleteSession(sessionId) {
      try {
        const accessToken = getAccessToken();
        await axios.post("/api/session/delete", {
          sessionId: sessionId
        }, {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        await this.getSessions();
      } catch (error) {
        console.error(error);
      }
    },
    formatLastActivity(lastActivity) {
      if (!lastActivity) return '';
      return format(new Date(lastActivity), 'yyyy-MM-dd HH:mm:ss');
    }
  }
}
</script>

<template>
  <div>
    <button @click="getSessions">Get sessions</button>
    <ul>
      <li v-for="session in sessions" :key="session.sessionId">
        {{ session.sessionId }} | {{ session.device }} | {{ session.service }} | {{ session.os }} | {{ session.location }} | {{ formatLastActivity(session.lastActivity) }}
        <button @click="deleteSession(session.sessionId)">Delete</button>
      </li>
    </ul>
  </div>
</template>