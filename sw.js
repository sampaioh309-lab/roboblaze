importScripts('https://www.gstatic.com/firebasejs/9.22.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.22.0/firebase-messaging-compat.js');

firebase.initializeApp({
  apiKey: "AIzaSyABwBwgQ3OREckh9LC8DRmN8KwOKKy6l_A",
  authDomain: "roboblaze-8dfde.firebaseapp.com",
  projectId: "roboblaze-8dfde",
  messagingSenderId: "88914971613",
  appId: "1:88914971613:web:a04143458a9ea4fdac0434"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
  self.registration.showNotification(payload.notification.title, {
    body: payload.notification.body,
    icon: "https://cdn-icons-png.flaticon.com/512/3523/3523887.png"
  });
});
