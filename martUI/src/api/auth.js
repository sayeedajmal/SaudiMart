// martUI/src/api/auth.js

import {
  signupSuccess,
  loginSuccess,
  authFailure
} from '../redux/authActions'; // Import your actions

const API_BASE_URL = 'YOUR_BACKEND_API_URL'; // Replace with your actual backend URL

export const signup = async (userData, dispatch) => { // Accept dispatch as an argument
  try {
    const response = await fetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    const data = await response.json();

    if (!response.ok) {
      const error = new Error(data.message || 'Signup failed');
      dispatch(authFailure(error)); // Dispatch failure action
      throw error;
    }

    dispatch(signupSuccess(data.result)); // Dispatch success action with the data.result
    return data.result; // Return the data for potential use
  } catch (error) {
    console.error('Signup error:', error);
    dispatch(authFailure(error)); // Dispatch failure action
    throw error;
  }
};

export const login = async (credentials, dispatch) => { // Accept dispatch as an argument
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
      dispatch(authFailure(error)); // Dispatch failure action
      throw error;
    }

    dispatch(loginSuccess(data.result)); // Dispatch success action with the data.result
    return data.result; // Return the data for potential use
  } catch (error) {
    console.error('Login error:', error);
    dispatch(authFailure(error)); // Dispatch failure action
    throw error;
  }
};

export const refreshToken = async (refreshTokenData) => {
  // This endpoint is typically for server-side token refresh for security.
  // However, if you need a client-side refresh mechanism,
  // the implementation would be similar to login/signup, including error handling.

  console.warn("Client-side token refresh is generally not recommended due to security concerns. Consider implementing server-side token refresh.")

  // Add fetch logic here if needed for client-side refresh
  // try {...} catch (error) {...}
  // If implementing client-side refresh, you might dispatch an action to update the tokens in the Redux state on success.
};
