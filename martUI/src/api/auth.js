import {
  authFailure,
  loginSuccess,
  authRequest,
  signupSuccess
} from '../store/authActions';
import { addNotification } from '../store/notificationActions';

const API_BASE_URL = 'http://localhost:8081';

export const signup = async (userData, dispatch) => {
  try {
    dispatch(authRequest());
    const response = await fetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    const data = await response.json();
    console.log("WHAT: ", data);

    if (!response.ok) {
      const error = new Error(data.message || 'Signup failed');
      dispatch(addNotification(data.message || 'Signup failed', 'error'));
      dispatch(authFailure(error));
      throw error;
    }

    dispatch(signupSuccess(data.data));
    return data.data;
  } catch (error) {
    console.error('Signup error:', error);
    dispatch(authFailure(error));
    throw error;
  }
};

export const login = async (credentials, dispatch) => {
  console.log('Dispatching authRequest...');
  dispatch(authRequest());

  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });

    const data = await response.json();
    console.log('Login API response data:', data);

    if (!response.ok) {
      const error = new Error(data.message || 'Login failed');
      dispatch(addNotification(data.message || 'Login failed', 'error'));
      dispatch(authFailure(error));
      throw error;
    }

    dispatch(loginSuccess(data.data));  
    console.log('Dispatching loginSuccess...');
    return data.data;
  } catch (error) {
    console.error('Login error:', error);
    dispatch(authFailure(error));
    console.log('Dispatching authFailure...');
    throw error;
  }
};

export const refreshToken = async (refreshTokenData) => {




  console.warn("Client-side token refresh is generally not recommended due to security concerns. Consider implementing server-side token refresh.")


};
