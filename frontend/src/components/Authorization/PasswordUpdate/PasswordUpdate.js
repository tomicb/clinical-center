import React, { Component } from "react";
import authService from "../../../services/auth-service";
import axios from "axios";
import classes from './PasswordUpdate.module.css';

class PasswordUpdate extends Component {
  state = {
    oldPassword: "",
    password: "",
    repeatedPassword: "",
  };

  submitHandler = (e) =>{
    e.preventDefault();
    if(this.state.password.trim().length < 6 || this.state.repeatedPassword.trim().length < 6 ){
        alert("Password needs to have minimum 6 characters!");
        return;
    }else if(this.state.password !== this.state.repeatedPassword){
        alert("Passwords need to match!");
        return;
    }
    let data = {
        "oldPassword": this.state.oldPassword,
        "newPassword": this.state.password,
        "repeatedPassword": this.state.repeatedPassword
    }
    axios.put("/auth/changePassword", data)
    .then(response =>{
        if(authService.getCurrentRole() === "ROLE_FIRST_LOGIN") {
            authService.logout();
            alert("Please login again!");
        } else {
            alert("Successfully changed password");
        }
    })
    .catch(err =>{
        console.log(err);
    })
  }

  render() {
    return (
      <>
        <h1 className={classes.title}>Password change</h1>
        <form className={classes.form} onSubmit={this.submitHandler}>
          <input
            className={classes.input}
            type="password"
            placeholder="Old password"
            name="oldPassword"
            value={this.state.oldPassword}
            onChange={(event) => {
              this.setState({ oldPassword: event.target.value });
            }}
          />
          <input
            className={classes.input}
            type="password"
            placeholder="New password"
            name="newPassword"
            value={this.state.password}
            onChange={(event) => {
              this.setState({ password: event.target.value });
            }}
          />
          <input
            className={classes.input}
            type="password"
            placeholder="Repeated password"
            name="repeatedPassword"
            value={this.state.repeatedPassword}
            onChange={(event) => {
              this.setState({ repeatedPassword: event.target.value });
            }}
          />
          <input type="submit" placeholder="Submit" name="submit" value="Submit" className={classes.submit} />
        </form>
      </>
    );
  }
}

export default PasswordUpdate;
