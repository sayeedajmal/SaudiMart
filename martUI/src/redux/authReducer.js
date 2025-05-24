import {
  AUTH_FAILURE,
  AUTH_REQUEST,
  LOGIN_SUCCESS,
  LOGOUT,
  SIGNUP_SUCCESS,
} from './authActions';

const initialState = {
  isAuthenticated: false,
  user: null,
  tokens: null,
  error: null,
  loading: false,
};

const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case AUTH_REQUEST:
      return {
        ...state,
        loading: true,
        error: null,
      };
    case SIGNUP_SUCCESS:
    case LOGIN_SUCCESS:

      if (action.payload && action.payload.data) {
        return {
          ...state,
          isAuthenticated: true,
          user: action.payload.data.myProfile,
          tokens: {
            accessToken: action.payload.data.accessToken,
            refreshToken: action.payload.data.refreshToken,
          },
          error: null,
          loading: false,
        };
      }

      return state;
    case LOGOUT:
      return {
        ...initialState,
        loading: false,
      };
    case AUTH_FAILURE:
      return {
        ...state,
        isAuthenticated: false,
        user: null,
        tokens: null,
        error: action.payload,
        loading: false,
      };
    default:
      return state;
  }
};

export default authReducer;