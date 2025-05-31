export const SIGNUP_SUCCESS = 'SIGNUP_SUCCESS';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGOUT = 'LOGOUT';
export const AUTH_FAILURE = 'AUTH_FAILURE';
export const AUTH_REQUEST = 'AUTH_REQUEST';
export const LOAD_USER_FROM_LOCAL_STORAGE = 'LOAD_USER_FROM_LOCAL_STORAGE';

export const signupSuccess = (userData) => ({
  type: SIGNUP_SUCCESS,
  payload: userData,

});

export const loginSuccess = (userData) => {
  try {
    localStorage.setItem('userData', JSON.stringify(userData));
  } catch (e) {
    console.error('Error saving user data to localStorage:', e);
  }
  return {
    type: LOGIN_SUCCESS,
    payload: userData,
  };
};

export const logout = () => {
  try {
    localStorage.removeItem('userData');
  } catch (e) {
    console.error('Error removing user data from localStorage:', e);
  }
  return {
    type: LOGOUT,
  };
};

export const authFailure = (error) => ({
  type: AUTH_FAILURE,
  payload: error,
});

export const authRequest = () => ({
  type: AUTH_REQUEST,
});

export const loadUserFromLocalStorage = () => {
  try {
    const userDataString = localStorage.getItem('userData');
    console.log("HELLO",userDataString);
    
    if (userDataString) {
      const userData = JSON.parse(userDataString);
      return {
        type: LOAD_USER_FROM_LOCAL_STORAGE,
        payload: userData,
      };
    }
  } catch (e) {
    console.error('Error loading user data from localStorage:', e);
  }

  return {
    type: 'NO_USER_IN_STORAGE',
    payload: null,
  };
};