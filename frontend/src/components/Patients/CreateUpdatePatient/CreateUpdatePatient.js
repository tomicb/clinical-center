import React, { Component } from "react";
import axios from "axios";
import { Col, Row } from "react-bootstrap";
import classes from "./CreateUpdatePatient.module.css";
import { Link, withRouter } from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from '@fortawesome/free-solid-svg-icons';

class CreateUpdatePatient extends Component {
  state = {
    firstName: "",
    lastName: "",
    jmbg: "",
    address: "",
    email: "",
    password: "",
    gender: "MALE",
    creating: false,
    showPasswordChangeBtn: false
  };

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  }


  componentDidMount = () => {
    this.setState({ creating: this.props.creating });
    let passInput = document.getElementById("password");
    let lblPass = document.getElementById("lblPassword");
    if (!this.props.creating) {
      
      passInput.style.display = "none";
      lblPass.style.display = "none";
    
      const jmbg = this.props.match.params.jmbg;
      
      if(jmbg === undefined){
        let delBtn = (document.getElementById("del").style.display = "none");
        axios
          .get("/patients/patientInfo")
          .then((response) => {
            this.setState({
              firstName: response.data.firstName,
              lastName: response.data.lastName,
              jmbg: response.data.jmbg,
              address: response.data.address,
              email: response.data.email,
              gender: response.data.gender,
              showPasswordChangeBtn: true
            });
            return;
          })
          .catch((err) => {
            console.error(err);
            return;
          });
      }else{
        axios
          .get("/patients/" + jmbg)
          .then((response) => {
            this.setState({
              firstName: response.data.firstName,
              lastName: response.data.lastName,
              jmbg: response.data.jmbg,
              address: response.data.address,
              email: response.data.email,
              gender: response.data.gender,
            });
          })
          .catch((err) => {
            console.error(err);
          });
      }  
    }else{
      let delBtn = document.getElementById("del").style.display = "none";
    }
  };

  cancelHandler = (e) =>{
    e.preventDefault();
    window.history.go(-1);
  }

  blockHandler = (e) =>{
    e.preventDefault();
    axios.put("/users/block/" + this.state.jmbg )
    .then(response =>{
      window.history.go(-1);
    })
    .catch(err =>{
      alert("Patient can't be blocked!");
    });
  }


  submitHandler = (e) => {
    e.preventDefault();
    
    

    var DD = this.state.jmbg[0] + this.state.jmbg[1];
    var MM = this.state.jmbg[2] + this.state.jmbg[3];
    var GGG = this.state.jmbg[4] + this.state.jmbg[5] + this.state.jmbg[6];


    if (this.state.firstName.length < 1) {
      alert("Invalid first name");
      return;
    } else if (this.state.lastName.length < 1) {
      alert("Invalid last name");
      return;
    } else if (this.state.jmbg.length !== 13) {
      alert("JMBG must have 13 digits");
      return;
    } else if (this.state.address.length < 1) {
      alert("Invalid address");
      return;
    } else if (this.state.jmbg.length !== 13) {
      alert("JMBG must have 13 digits!");
      return;
    } else if (Number(DD) < 1 || Number(DD) > 31) {
      alert("JMBG is not valid! ");
      return;
    } else if (Number(MM) < 1 || Number(MM > 12)) {
      alert("JMBG is not valid! ");
      return;
    } else if (Number(DD) === 29 && Number(MM) === 2 && Number(GGG) % 4 !== 0) {
      alert("JMBG is not valid! ");
      return;
    } else if (Number(DD) > 28 && Number(MM)) {
      alert("JMBG is not valid! ");
      return;
    } else if (!this.validateEmail(this.state.email)) {
      alert("Email is not valid! ");
      return;
    } else {
      if (this.state.creating) {
        if (data.gender === undefined) {
          data.gender = "MALE";
        }
        if (this.state.password.length < 6) {
          alert("Password too short!");
          return;
        }
        let data = {
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          jmbg: this.state.jmbg,
          address: this.state.address,
          email: this.state.email,
          password: this.state.password,
          gender: this.state.gender,
        };
        console.log(data.gender);
        axios
          .post("/patients/add", data)
          .then((response) => {
            window.history.go(-1);
          })
          .catch((err) => {
            alert(err);
          });
      } else {
        let data = {
          firstName: this.state.firstName,
          lastName: this.state.lastName,
          jmbg: this.state.jmbg,
          address: this.state.address,
          email: this.state.email,
          gender: this.state.gender,
        };
        axios
          .put("/patients/update", data)
          .then((response) => {
            window.history.go(-1);
          })
          .catch((err) => {
            alert(err);
          });
      }
    }
  };

  render() {

    let naslov = <div style={{textAlign:"center", marginTop: "70px"}}>
                <h1>Welcome to your profile</h1>
                <p style={{ marginTop: "20px"}}><i>Thank you for using our services!</i></p><br/>
                <FontAwesomeIcon style={{ marginTop: "20px"}} icon={faUser} size="10x"/>
                </div>

    if(this.state.creating){
      naslov = null;
    }

    return (
      <div className={classes.maindiv}>
        {naslov}
        <form className={classes.form} onSubmit={this.submitHandler}>
          <Row>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>First name</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                className={classes.oneinput}
                type="text"
                name="firstName"
                value={this.state.firstName}
                onChange={(event) =>
                  this.setState({ firstName: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>Last name</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                className={classes.oneinput}
                type="text"
                name="lastName"
                value={this.state.lastName}
                onChange={(event) =>
                  this.setState({ lastName: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>JMBG</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                className={classes.oneinput}
                readOnly={!this.state.creating}
                type="text"
                name="jmbg"
                value={this.state.jmbg}
                onChange={(event) =>
                  this.setState({ jmbg: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>Address</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                className={classes.oneinput}
                type="text"
                name="Address"
                value={this.state.address}
                onChange={(event) =>
                  this.setState({ address: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>Email</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                className={classes.oneinput}
                type="text"
                name="Email"
                value={this.state.email}
                onChange={(event) =>
                  this.setState({ email: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label id="lblPassword">Password</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input
                id="password"
                className={classes.oneinput}
                type="password"
                name="password"
                value={this.state.password}
                onChange={(event) =>
                  this.setState({ password: event.target.value })
                }
              />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <label>Gender</label>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <select
                className={classes.select}
                name="gender"
                value={this.state.gender}
                onChange={(event) =>
                  this.setState({ gender: event.target.value })
                }
              >
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
              </select>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <button
                className="btn btn-secondary"
                onClick={this.cancelHandler}
              >
                Cancel
              </button>
            </Col>
            <Col lg={6} md={6} sm={6} xs={6}>
              <input type="submit" value="submit" className="btn btn-success" />
            </Col>
            <Col lg={6} md={6} sm={6} xs={6} className="text-center" id="del">
              <button
                disabled={this.props.creating}
                className="btn btn-danger m-5 justify-center"
                onClick={this.blockHandler}
              >
                Block patient
              </button>
            </Col>
            {this.state.showPasswordChangeBtn && <Col
              lg={6}
              md={6}
              sm={6}
              xs={6}
              className="text-center"
              id="change"
            >
              <Link to = "/password-update">
                <button
                  className="btn btn-info m-5 justify-center"
                  onClick={this.changePasswordHandler}
                >
                  Change password
                </button>
              </Link>
            </Col>}
          </Row>
        </form>
      </div>
    );
  }
}

export default withRouter(CreateUpdatePatient);
