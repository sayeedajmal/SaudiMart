import {
  authFailure,
  loginSuccess,
  signupSuccess
} from '../redux/authActions';
import { addNotification } from '../redux/notificationActions';

const API_BASE_URL = 'http://localhost:8081';

export const signup = async (userData, dispatch) => {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    const data = await response.json();
    console.log("WHAT: ",data);
    
    if (!response.ok) {
      const error = new Error(data.message || 'Signup failed');
      dispatch(addNotification({ message: data.message || 'Signup failed', type: 'error' }));
      dispatch(authFailure(error));
      throw error;
    }

    dispatch(signupSuccess(data.result));
    return data.result;
  } catch (error) {
    console.error('Signup error:', error);
    dispatch(authFailure(error));
    throw error;
  }
};

export const login = async (credentials, dispatch) => {
  try {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });

    const data = await response.json();

    if (!response.ok) {
      const error = new Error(data.message || 'Login failed');
      dispatch(addNotification({ message: data.message || 'Login failed', type: 'error' }));
      dispatch(authFailure(error));
      throw error;
    }

    dispatch(loginSuccess(data.result));
    return data.result;
  } catch (error) {
    console.error('Login error:', error);
    dispatch(authFailure(error));
    throw error;
  }
};

export const refreshToken = async (refreshTokenData) => {




  console.warn("Client-side token refresh is generally not recommended due to security concerns. Consider implementing server-side token refresh.")




};
