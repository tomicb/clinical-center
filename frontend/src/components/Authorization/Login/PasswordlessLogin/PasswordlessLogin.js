import React, { Component } from "react";
import classes from "./PasswordlessLogin.module.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import axios from "./../../authAxios";

class PasswordlessLogin extends Component {
    
    state = {
        email: "",
        emailValidation: false,
        wrongEmail: false,
        isLoginBtnClicked: false,
        loginBtnClickedMsg: "",
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
        if (!this.validateEmail(this.state.email)) {
            this.setState({emailValidation: true});
            return;
        }
    
        this.setState( {
            wrongData: false,
            userBlocked: false,
            userPending: false,
            userNotVerified: false
        });
        
        if(!this.state.isLoginBtnClicked) {
            axios
                .post("/auth/login/passwordless", {email: this.state.email})
                .then((response) => {
                    this.setState({
                        isLoginBtnClicked: true,
                        loginBtnClickedMsg: "We have sent a login link to your email."
                    });
                })
                .catch(error => {
                    console.error(error);
                    if(error.response.status === 406) {
                        this.setState({isLoginBtnClicked: true,loginBtnClickedMsg: "The login link is already sent."});
                    } else if(error.response.status === 403) {
                        if(error.response.data.status === "PENDING")
                            this.setState({ userPending: true });
                        else if(error.response.data.status === "NOT_VERIFIED")
                            this.setState({ userNotVerified: true });
                        else
                            this.setState({ userBlocked: true });
                    }
                });
        } else {
            this.setState({loginBtnClickedMsg: "The login link is already sent."});
        }
    }
    
    render() {
        
        let error = null;
        if(this.state.wrongEmail) {
            error =
                <div className={classes.errorDiv}>
                    <span>The email is invalid!</span>
                </div>
        } else if(this.state.userBlocked) {
            error =
                <div className={classes.errorDiv}>
                    <span>Your account is blocked!</span>
                </div>
        } else if(this.state.userPending) {
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
                <h3>Login</h3>
                <h6 className="mb-4">Without password</h6>
                <div className={this.state.emailValidation ? [classes.inputItem, classes.errorBorder].join(" ") : classes.inputItem}>
                    <FontAwesomeIcon icon={faEnvelope} size="1x" className={classes.inputItemIcon}/>
                    <input type="email" placeholder="Email" value={this.state.email}
                       onChange={event => this.emailInputHandler(event)} onBlur={this.checkEmailHandler}/>
                </div>
                {error}
                <button className={classes.loginBtn} onClick={this.loginHandler}>Login</button>
                { this.state.isLoginBtnClicked ? <p className="d-block mt-2" style={{fontSize: "1.1rem"}}>{this.state.loginBtnClickedMsg}</p> : null}
                <p className="mt-4">
                    <span className={classes.spanText}>NOTE: </span>
                    We will send a login link to your email address. The link will be active for only 10
                    minutes.
                </p>
            </>
        );
    }
}

export default PasswordlessLogin;