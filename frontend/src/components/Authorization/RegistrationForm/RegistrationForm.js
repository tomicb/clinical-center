import React, { Component } from "react";
import axios from "axios";
import { Col, Row } from "react-bootstrap";
import classes from "./RegistrationForm.module.css";
import { withRouter, Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faIdCard,
  faHome,
  faEnvelope,
  faUnlock,
  faVenusMars,
  faClinicMedical,
} from "@fortawesome/free-solid-svg-icons";
import moment from "moment";
import { isNumber } from "chart.js/helpers";


class RegistrationForm extends Component {
  state = {
    firstName: "",
    lastName: "",
    jmbg: "",
    address: "",
    email: "",
    password: "",
    repeatedPassword: "",
    gender: "MALE",
    emailValidation: false,
    wrongEmail: false,
    errorValidation: {
      firstName: false,
      lastName: false,
      jmbg: false,
      jmbgTaken: false,
      address: false,
      email: false,
      emailTaken: false,
      password: false,
    },
  };

  validateEmail(email) {
    const re =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }

  firstNameInputHandler = (event) => {
    this.setState({
      firstName: event.target.value,
      errorValidation: {
        ...this.state.errorValidation,
        firstName: event.target.value.trim() === "",
      },
    });
  };

  lastNameInputHandler = (event) => {
    this.setState({
      lastName: event.target.value,
      errorValidation: {
        ...this.state.errorValidation,
        lastName: event.target.value.trim() === "",
      },
    });
  };

  jmbgInputHandler = (event) => {
    const jmbg = event.target.value.trim();

    const DD = jmbg.substring(0, 2);
    const MM = jmbg.substring(2, 4);
    const GGGG =
      jmbg[4] === "0" ? "2" + jmbg.substring(4, 7) : "1" + jmbg.substring(4, 7);

    const error =
      jmbg.length !== 13 ||
      !moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid();

    this.setState({
      jmbg: jmbg,
      errorValidation: { ...this.state.errorValidation, jmbg: error },
    });
  };


  addressInputHandler = (event) => {
    this.setState({
      address: event.target.value,
      errorValidation: {
        ...this.state.errorValidation,
        address: event.target.value.trim() === "",
      },
    });
  };

  genderSelectHandler = (gender) => {
    this.setState({ gender: gender.value });
  };

  emailInputHandler = (event) => {
    this.setState({
      email: event.target.value.trim(),
      errorValidation: {
        ...this.state.errorValidation,
        email: event.target.value.trim() === "",
      },
    });
  };


  passwordInputHandler = (event) => {
    this.setState({
      password: event.target.value,
      errorValidation: {
        ...this.state.errorValidation,
        password: event.target.value.length < 6,
      },
    });
  };

  submitHandler = event =>{
    event.preventDefault();
    
    const data = {
      firstName: this.state.firstName.trim(),
      lastName: this.state.lastName.trim(),
      jmbg: this.state.jmbg.trim(),
      address: this.state.address.trim(),
      email: this.state.email.trim().toLowerCase(),
      password: this.state.password,
      repeatedPassword: this.state.repeatedPassword,
      gender: this.state.gender,
    };
    console.log(data);

    if (data.firstName === "") {
      this.setState({
        errorValidation: { ...this.state.errorValidation, firstName: true },
      });
    }

    if(data.password !== data.repeatedPassword){
      alert("Passwords do not match");
    }

    if (data.lastName === "") {
      this.setState({
        errorValidation: { ...this.state.errorValidation, lastName: true },
      });
    }

    if (data.jmbg.length !== 13 || !isNumber(data.jmbg)) {
      this.setState({
        errorValidation: { ...this.state.errorValidation, jmbg: true },
      });
    }

    const DD = data.jmbg.substring(0, 2);
    const MM = data.jmbg.substring(2, 4);
    const GGGG =
      data.jmbg[4] === "0"
        ? "2" + data.jmbg.substring(4, 7)
        : "1" + data.jmbg.substring(4, 7);
    if (!moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid()) {
      this.setState({
        errorValidation: { ...this.state.errorValidation, jmbg: true },
      });
    }

    if (data.address === "") {
      this.setState({
        errorValidation: { ...this.state.errorValidation, email: true },
      });
    }

    if (!this.validateEmail(data.email)) {
      this.setState({
        errorValidation: { ...this.state.errorValidation, email: true },
      });
    }

    if (data.password.length < 6) {
      this.setState({
        errorValidation: { ...this.state.errorValidation, password: true },
      });
    }

    if (
      data.firstName === "" ||
      data.lastName === "" ||
      data.jmbg.length !== 13 ||
      !isNumber(data.jmbg) ||
      !moment(`${DD}/${MM}/${GGGG}`, "DD/MM/YYYY", true).isValid() ||
      data.address === "" ||
      !this.validateEmail(data.email) ||
      data.password.length < 6
    ){
      return;
    }

    axios.post("/auth/register", data)
    .then(response =>{
      alert("Successfully sent registration request!");
    })
    .catch(err =>{
      console.log(err);
    })
  }

  render() {
    let error = null;
    if (this.state.wrongEmail) {
      error = (
        <div className={classes.errorDiv}>
          <span>The email is invalid!</span>
        </div>
      );
    }

    return (
      <>
        <div className={classes.login}>
          <div className={classes.loginMain}>
            <h3>Register</h3>

            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faUser}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="text"
                placeholder="First name"
                value={this.state.firstName}
                onChange={(event) => this.firstNameInputHandler(event)}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faUser}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="text"
                placeholder="last name"
                value={this.state.lastName}
                onChange={(event) => this.lastNameInputHandler(event)}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faIdCard}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="text"
                placeholder="jmbg"
                value={this.state.jmbg}
                onChange={(event) => this.jmbgInputHandler(event)}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faHome}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="text"
                placeholder="address"
                value={this.state.address}
                onChange={(event) => this.addressInputHandler(event)}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faEnvelope}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="email"
                placeholder="Email"
                value={this.state.email}
                onChange={(event) => this.emailInputHandler(event)}
                onBlur={this.checkEmailHandler}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faUnlock}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="password"
                placeholder="password"
                value={this.state.password}
                onChange={(event) => this.passwordInputHandler(event)}
              />
            </div>
            <div
              className={
                this.state.emailValidation
                  ? [classes.inputItem, classes.errorBorder].join(" ")
                  : classes.inputItem
              }
            >
              <FontAwesomeIcon
                icon={faUnlock}
                size="1x"
                className={classes.inputItemIcon}
              />
              <input
                type="password"
                placeholder="repeated password"
                value={this.state.repeatedPassword}
                onChange={(event) =>
                  this.setState({ repeatedPassword: event.target.value })
                }
              />
            </div>
            <div className={classes.genders}>
              <select
                value={this.state.gender}
                onChange={(event) => this.genderSelectHandler(event)}
              >
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
              </select>
            </div>
            {error}
            <button className={classes.loginBtn} onClick={this.submitHandler}>
              Register
            </button>
            {this.state.isLoginBtnClicked ? (
              <p className="d-block mt-2" style={{ fontSize: "1.1rem" }}>
                {this.state.loginBtnClickedMsg}
              </p>
            ) : null}
            <p className="mt-4">
              <span className={classes.spanText}>NOTE: </span>
              We will send a login link to your email address after
              administrator approve your registration.
            </p>
          </div>
        </div>
        <p className="mt-3">
          Have account?{" "}
          <Link to="/login" className={classes.link}>
            Sign in
          </Link>
        </p>
      </>
    );
  }
}

export default RegistrationForm;