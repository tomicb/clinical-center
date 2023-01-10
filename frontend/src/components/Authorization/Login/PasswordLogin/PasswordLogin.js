import React, { Component } from "react";
import classes from "./PasswordLogin.module.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope, faUnlockAlt } from '@fortawesome/free-solid-svg-icons';
import axios from "./../../authAxios";
import authService from "../../../../services/auth-service";

class PasswordLogin extends Component {
    
    state = {
        email: "",
        password: "",
        emailValidation: false,
        wrongData: false,
        userBlocked: false,
        userPending: false,
        userNotVerified: false
    }
    
    validateEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }
    
    emailInputHandler = event => {
        this.setState({
            email: event.target.value.trim(),
            emailValidation: event.target.value.trim() === ""
        });
    }
    
    checkEmailHandler = () => {
        this.setState({ emailValidation: !this.validateEmail(this.state.email.trim())});
    }
    
    loginHandler = () => {
        const data = {
            email: this.state.email,
            password: this.state.password
        }
        
        if (!this.validateEmail(data.email)) {
            this.setState({emailValidation: true});
            return;
        }
        
        this.setState( {
            wrongData: false,
            userBlocked: false,
            userPending: false,
            userNotVerified: false
        });
        
        axios
            .post("/auth/login", data)
            .then((response) => {
                console.log(response);
                localStorage.setItem("accessToken", response.data.accessToken);
                localStorage.setItem("refreshToken", response.data.refreshToken);
                authService.setCurrentRoleFromToken();
            })
            .catch((error) => {
                if(error.response.status === 401)
                    this.setState({ wrongData: true });
                else if(error.response.status === 403) {
                    if(error.response.data.status === "PENDING")
                            this.setState({ userPending: true });
                        else if(error.response.data.status === "NOT_VERIFIED")
                            this.setState({ userNotVerified: true });
                        else
                            this.setState({ userBlocked: true });
                } else if(error.response.status === 406) {
                    localStorage.setItem("accessToken", error.response.data.accessToken);
                    localStorage.setItem("refreshToken", error.response.data.refreshToken);
                    authService.setCurrentRole("ROLE_FIRST_LOGIN");
                    window.location.replace("/clinic/password-update");
                }
            });
    }
    
    render() {
    
        let error = null;
        if(this.state.wrongData) {
            error =
                <div className={classes.errorDiv}>
                    <span>The email or password you entered is incorrect!</span>
                </div>
        }
        else if(this.state.userBlocked) {
            error =
                <div className={classes.errorDiv}>
                    <span>Your account is blocked!</span>
                </div>
        }else if(this.state.userPending) {
            error =
                <div className={classes.errorDiv}>
                    <span>Please wait until we approve your request!</span>
                </div>
        } else if(this.state.userNotVerified) {
            error =
                <div className={classes.errorDiv}>
                    <span>Please confirm your email!</span>
                </div>
        }
        
        return (
            <>
                <h3 className="mb-4">Login</h3>
                <div className={this.state.emailValidation ? [classes.inputItem, classes.errorBorder].join(" ") : classes.inputItem}>
                    <FontAwesomeIcon icon={faEnvelope} size="1x" className={classes.inputItemIcon}/>
                    <input type="email" placeholder="Email" value={this.state.email}
                           onChange={event => this.emailInputHandler(event)} onBlur={this.checkEmailHandler}/>
                </div>
                <div className={this.state.wrongPassword ? [classes.inputItem, classes.errorBorder].join(" ") : classes.inputItem}>
                    <FontAwesomeIcon icon={faUnlockAlt} size="1x" className={classes.inputItemIcon}/>
                    <input type="password" placeholder="Password" value={this.state.password}
                           onChange={event => this.setState({password: event.target.value})}/>
                </div>
                {error}
                <button className={classes.loginBtn} onClick={this.loginHandler}>Login</button>
            </>
        );
    }
}

export default PasswordLogin;