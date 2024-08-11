import socket from '@/ws';
import { Client } from '@stomp/stompjs';
import { getRefreshToken } from "@/js/token";

export function sessionSubscribe() {
    const stompClient = new Client({
        webSocketFactory: () => socket,
        onConnect: () => {
            stompClient.subscribe('/session', (message) => {
                try {
                    const parsedMessage = JSON.parse(message.body);
                    const currentRefreshToken = getRefreshToken();

                    if (parsedMessage.refreshToken === currentRefreshToken) {
                        window.location.reload();
                    }
                } catch (error) {
                    console.error('Error parsing message body:', error);
                }
            });
        },
        onStompError: (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        },
    });
    stompClient.activate();
}