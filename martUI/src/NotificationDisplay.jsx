import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { removeNotification } from './redux/notificationActions';

const NotificationDisplay = () => {
  const notifications = useSelector(state => state.notifications);
  const dispatch = useDispatch();

  useEffect(() => {
    if (notifications.length > 0) {
      const timer = setTimeout(() => {
        dispatch(removeNotification(notifications[0].id));
      }, 5000); // Auto-dismiss after 5 seconds

      return () => clearTimeout(timer);
    }
  }, [notifications, dispatch]);

  const handleCloseNotification = (id) => {
    dispatch(removeNotification(id));
  };

  return (
    <div className="fixed top-4 right-4 z-50 space-y-2">
      {notifications.map(notification => (
        <div
          key={notification.id}
          className={`p-4 rounded shadow-md text-white ${
            notification.type === 'success' ? 'bg-success' :
            notification.type === 'error' ? 'bg-error' :
            notification.type === 'warning' ? 'bg-warning' :
            'bg-gray-500'
          }`}
        >
          <div className="flex justify-between items-center">
            <span>{notification.message}</span>
            <button
              onClick={() => handleCloseNotification(notification.id)}
              className="ml-4 text-white font-bold"
            >
              &times;
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default NotificationDisplay;