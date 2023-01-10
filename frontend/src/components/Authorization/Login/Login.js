import React, { Component } from "react";
import classes from './Login.module.css';
import PasswordlessLogin from './PasswordlessLogin/PasswordlessLogin';
import {Link} from "react-router-dom";
import PasswordLogin from './PasswordLogin/PasswordLogin';


class Login extends Component {
    
    state = {
        passwordLogin: true
    }
    
    passwordLoginBtnHandler = () => {
        this.setState({ passwordLogin: true });
    }
    
    passwordlessLoginBtnHandler = () => {
        this.setState({ passwordLogin: false });
    }
    
    render() {
        
        return (
            <>
                <div className={classes.login}>
                    <div className={classes.heading}>
                        <button className={ this.state.passwordLogin ? [classes.firstBtn, classes.btnBorder].join(" ") : classes.firstBtn}
                            onClick={this.passwordLoginBtnHandler}>Login with password</button>
                        <button className={ !this.state.passwordLogin ? [classes.secoundBtn, classes.btnBorder].join(" ") : classes.secoundBtn}
                            onClick={this.passwordlessLoginBtnHandler}>Login without password</button>
                    </div>
                    <div className={classes.loginMain}>
                        {this.state.passwordLogin ?
                            <PasswordLogin/> :
                            <PasswordlessLogin/> }
                    </div>
                </div>
                <p className="mt-3">Dont have account yet? <Link to="/registration" className={classes.link}>Register now!</Link></p>
            </>
        );
    }
}

export default Login;