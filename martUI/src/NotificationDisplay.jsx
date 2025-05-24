import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { removeNotification } from "./redux/notificationActions";

const NotificationDisplay = () => {
  const notifications = useSelector((state) => state.notifications);
  const dispatch = useDispatch();

  useEffect(() => {
    if (notifications.length > 0) {
      const timer = setTimeout(() => {
        dispatch(removeNotification(notifications[0].id));
      }, 5000);

      return () => clearTimeout(timer);
    }
  }, [notifications, dispatch]);

  const handleCloseNotification = (id) => {
    dispatch(removeNotification(id));
  };

  const getNotificationIcon = (type) => {
    switch (type) {
      case "success":
        return "check_circle";
      case "error":
        return "error";
      case "warning":
        return "warning";
      case "info":
      default:
        return "info";
    }
  };

  return (
    <div className="fixed top-4 right-4 z-50 space-y-3">
      {" "}
      {/* Increased space-y */}
      {notifications.map((notification) => (
        <div
          key={notification.id}
          className={`p-4 rounded-lg shadow-xl text-white flex items-center transition-opacity ease-in-out duration-300 ${
            notification.type === "success"
              ? "bg-success"
              : notification.type === "error"
              ? "bg-error"
              : notification.type === "warning"
              ? "bg-warning"
              : "bg-info"
          }`}
        >
          {/* Notification Icon */}
          <span className="material-symbols-outlined mr-3 text-2xl">
            {getNotificationIcon(notification.type)}
          </span>

          {/* Notification Message */}
          <div className="flex-grow">
            <span>{notification.message}</span>
          </div>

          {/* Close Button */}
          <button
            onClick={() => handleCloseNotification(notification.id)}
            className="ml-4 p-1 rounded-full hover:bg-white hover:bg-opacity-20 transition-colors"
          >
            <span className="material-symbols-outlined text-white text-lg">
              close
            </span>{" "}
            {/* Close icon */}
          </button>
        </div>
      ))}
    </div>
  );
};

export default NotificationDisplay;
