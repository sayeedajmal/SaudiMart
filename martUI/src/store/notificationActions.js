// notificationsActions.js
export const ADD_NOTIFICATION = 'ADD_NOTIFICATION';
export const REMOVE_NOTIFICATION = 'REMOVE_NOTIFICATION';

let nextNotificationId = 0;

export const addNotification = (message, type = 'info') => ({
  type: ADD_NOTIFICATION,
  payload: {
    id: nextNotificationId++,
    message,
    type,
  },
});

export const removeNotification = (id) => ({
  type: REMOVE_NOTIFICATION,
  payload: id,
});