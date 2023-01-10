import React, { Component } from "react";
import classes from "./Patients.module.css";
import axios from "axios";
import Patient from "./Patient/Patient";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import authService from "../../services/auth-service";

class Patients extends Component {
  state = {
    patients: [],
    firstName: "",
    lastName: "",
    jmbg: "",
  };

  //Adding all patients to the state
  componentDidMount = () => {
    axios
      .get("/patients/all")
      .then((response) => {
        this.setState({ patients: response.data });
      })
      .catch((error) => console.log(error));
  };

  //Adding only patients that meet the conditions
  submitHandler = (e) => {
    e.preventDefault();
    let firstName = this.state.firstName;
    let lastName = this.state.lastName;
    let jmbg = this.state.jmbg;

    axios
      .get(
        `/patients/searched?firstName=${firstName}&lastName=${lastName}&jmbg=${jmbg}`
      )
      .then((response) => {
        this.setState({ patients: response.data });
      })
      .catch((error) => console.log(error));
  };

  render() {
    let patients = (
      <h2 style={{ textAlign: "center", marginTop: "30px", color: "#5d001e" }}>
        There are no patients
      </h2>
    );

    if (this.state.patients.length > 0) {
      patients = this.state.patients.map((patient) => {
        return (
          <Patient
            key={patient.jmbg}
            firstName={patient.firstName}
            lastName={patient.lastName}
            jmbg={patient.jmbg}
            email={patient.email}
            address={patient.address}/>
        );
      });
    };

    return (
      <>
        <h1 className={["text-center", "my-5", classes.title].join(" ")}>
          Patients search
        </h1>
        <form className={["mb-5", classes.form].join(" ")} onSubmit={this.submitHandler}>
          <input
            type="text"
            placeholder="FirstName"
            name="firstName"
            value={this.state.firstName}
            onChange={(event) =>
              this.setState({ firstName: event.target.value })
            }/>
          <input
            type="text"
            placeholder="LastName"
            name="lastName"
            value={this.state.lastName}
            onChange={(event) =>
              this.setState({ lastName: event.target.value })
            }/>
          <input
            type="text"
            placeholder="Jmbg"
            name="jmbg"
            value={this.state.jmbg}
            onChange={(event) => this.setState({ jmbg: event.target.value })}/>
          <input
            type="submit"
            value="Submit"
            name="submit"
            className={["btn", "btn-success", classes.submit].join(" ")}/>
        </form>
        {patients}
        {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && <div className="d-flex justify-content-center">
          <Link to="/patients/create" className={classes.createPatientBtn}>
            <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
            <span>Add a new patient</span>
          </Link>
        </div>}
      </>
    );
  }
}

export default Patients;
