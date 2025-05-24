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
      const newState = {
        ...state,
        loading: true,
        error: null,
      };
 console.log('Auth Reducer - AUTH_REQUEST Case - New State:', newState);
 return newState;
    case SIGNUP_SUCCESS:
    case LOGIN_SUCCESS:
      console.log('Auth Reducer - Inside LOGIN_SUCCESS case. Action:', action);
 console.log('Auth Reducer - LOGIN_SUCCESS if condition result:', action.payload && action.payload.data);
      if (action.payload) {
 console.log('Auth Reducer - Inside LOGIN_SUCCESS if block. action.payload.data:', action.payload.data);
        const returnedState = {
          ...state,
          isAuthenticated: true,
          user: action.payload.myProfile,
          tokens: {
            accessToken: action.payload.accessToken,
            refreshToken: action.payload.refreshToken,
          },
          error: null,
          loading: false,
        };
        console.log('Auth Reducer - LOGIN_SUCCESS - New State:', returnedState);
        return returnedState;
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