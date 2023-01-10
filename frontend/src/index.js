import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios'

axios.defaults.baseURL='https://localhost:8080/api';

axios.interceptors.request.use(request => {
  request.headers.Authorization = "Bearer " + localStorage.getItem("accessToken");
  return request;
});

axios.interceptors.response.use(response => {
  return response;
}, error => {
  const originalRequest = error.config;

  if (originalRequest.url === '/auth/refresh') {
      localStorage.clear();
      window.location.replace("/login");
      return Promise.reject(error);
  }

  if (error.response.status === 401) {
      return axios.post('/auth/refresh', localStorage.getItem("refreshToken"))
          .then(res => {
              if (res.status === 201) {
                  localStorage.setItem("accessToken", res.data);
                  return axios(originalRequest);
              }
          })
  }

  return Promise.reject(error);
});

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
