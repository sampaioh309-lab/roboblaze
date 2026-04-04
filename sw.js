self.addEventListener('install', e => {
  self.skipWaiting();
});

self.addEventListener('fetch', e => {});

// 🔔 NOTIFICAÇÃO
self.addEventListener('push', function(event) {
  const data = event.data ? event.data.text() : "Sinal disponível!";

  event.waitUntil(
    self.registration.showNotification("🚨 ROBÔ DOUBLE", {
      body: data,
      icon: "https://cdn-icons-png.flaticon.com/512/3523/3523887.png"
    })
  );
});
