export const SIGNUP_SUCCESS = 'SIGNUP_SUCCESS';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGOUT = 'LOGOUT';
export const AUTH_FAILURE = 'AUTH_FAILURE';

export const signupSuccess = (userData) => ({
 type: SIGNUP_SUCCESS,
  payload: userData, // authData should contain { accessToken, refreshToken, myProfile }
});

export const loginSuccess = (userData) => ({
 type: LOGIN_SUCCESS,
  payload: userData, // authData should contain { accessToken, refreshToken, myProfile }
});

export const logout = () => ({
  type: LOGOUT,
});

export const authFailure = (error) => ({
  type: AUTH_FAILURE,
  payload: error, // Should include an error message or object
});