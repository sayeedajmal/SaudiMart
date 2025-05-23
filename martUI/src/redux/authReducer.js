import {
  SIGNUP_SUCCESS,
  LOGIN_SUCCESS,
  LOGOUT,
  AUTH_FAILURE,
} from './authActions';

const initialState = {
  isAuthenticated: false,
  user: null,
  tokens: null,
  error: null,
};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case SIGNUP_SUCCESS:
    case LOGIN_SUCCESS:
      return {
        ...state,
        isAuthenticated: true,
        user: action.payload.myProfile, // Store the user object
        tokens: {
          accessToken: action.payload.accessToken,
          refreshToken: action.payload.refreshToken,
        }, // Store tokens as an object
        error: null,
      };
    case LOGOUT:
      return {
        ...initialState, // Reset state to initial state on logout
      };
    case AUTH_FAILURE:
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        tokens: null,
        error: action.payload, // Assuming payload is the error object or message
      };
    default:
      return state;
  }
};

export default authReducer;