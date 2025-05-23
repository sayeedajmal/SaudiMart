import { combineReducers } from 'redux';
import authReducer from './authReducer.js';
// Import other reducers here

const rootReducer = combineReducers({
  auth: authReducer,
  // Add other reducers here
});

export default rootReducer;
