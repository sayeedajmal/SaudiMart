import { combineReducers, createStore, applyMiddleware } from 'redux';
import authReducer from './authReducer.js';
// Import other reducers here
import notificationReducer from './notificationReducer.js';

// Simple logging middleware
const loggingMiddleware = store => next => action => {
  console.log('Action Dispatched:', action);
  console.log('Previous State:', store.getState());
  const result = next(action);
  console.log('Next State:', store.getState());
  return result;
};

const rootReducer = combineReducers({
  auth: authReducer,
  notifications: notificationReducer,
});

const store = createStore(rootReducer, applyMiddleware(loggingMiddleware));

export default store;
