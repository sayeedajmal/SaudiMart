import React from 'react';
import './FullScreenLoader.css';

const FullScreenLoader = () => {
  return (
    <div className="full-screen-loader-overlay">
      <div className="spinner"></div>
    </div>
  );
};

export default FullScreenLoader;