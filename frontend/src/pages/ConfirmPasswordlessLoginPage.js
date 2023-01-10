import React, { Component } from "react";
import queryString from 'query-string';
import axios from "../components/Authorization/authAxios";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHandHoldingHeart} from '@fortawesome/free-solid-svg-icons';
import {Link} from "react-router-dom";
import authService from "../services/auth-service";

class ConfirmPasswordlessLoginPage extends Component {
    
    componentDidMount() {
        const params = queryString.parse(this.props.location.search);
        const token = params.token;
        axios
            .get(`/auth/login/passwordless?token=${token}`)
            .then(response => {
                localStorage.setItem("accessToken", response.data.accessToken);
                localStorage.setItem("refreshToken", response.data.refreshToken);
                authService.setCurrentRoleFromToken();
            })
            .catch(error => {
                if(error.response.status === 406) {
                    localStorage.setItem("accessToken", error.response.data.accessToken);
                    localStorage.setItem("refreshToken", error.response.data.refreshToken);
                    authService.setCurrentRole("ROLE_FIRST_LOGIN");
                    window.location.replace("/password-update");
                }
            });
    }
    
    render() {
        
        return (
            <>
                <div className="d-flex justify-content-center align-items-center flex-column" style={{height: "100vh"}}>
                    <FontAwesomeIcon icon={faHandHoldingHeart} style={{color: "#5d001e", fontSize: "12rem"}}/>
                    <h2 style={{fontFamily: "'Kaushan Script', cursive", color: "#5d001e", fontSize: "5rem"}}>Clinic Centre</h2>
                    <h1 className="text-center mt-5 mb-4" style={{color: "#5d001e"}}>The link you clicked is either already used or invalid.</h1>
                    <div className="d-flex justify-content-center mb-5">
                        <Link to="/login" className="btn btn-lg btn-outline-dark">Go back to login</Link>
                    </div>
                </div>
            </>
        );
    }
}

export default ConfirmPasswordlessLoginPage;