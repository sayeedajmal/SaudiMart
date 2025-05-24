import { combineReducers, createStore } from 'redux';
import authReducer from './authReducer.js';
// Import other reducers here
import notificationReducer from './notificationReducer.js';

const rootReducer = combineReducers({
  auth: authReducer,
  notifications: notificationReducer,
});

const store = createStore(rootReducer);

export default store;
