import React, { Component } from "react";
import axios from "axios";
import { Col, Row } from "react-bootstrap";
import classes from './CreateUpdateNurse.module.css';
import { Link, withRouter } from "react-router-dom";

class CreateUpdateNurse extends Component {
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
    const re =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
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

      if (jmbg === undefined) {
        let delBtn = (document.getElementById("del").style.display = "none");
        axios
          .get("/nurses/nurseInfo")
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
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        
        axios
          .get("/nurses/" + jmbg)
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
    } else {
      let delBtn = (document.getElementById("del").style.display = "none");
    }
  };

  cancelHandler = (e) => {
    e.preventDefault();
    window.history.go(-1);
  };

  blockHandler = (e) => {
    e.preventDefault();
    axios
      .put("/users/block/" + this.state.jmbg)
      .then((response) => {
        window.history.go(-1);
      })
      .catch((err) => {
        alert("Nurse can't be blocked!");
      });
  };

  submitHandler = (e) => {
    e.preventDefault();
    const data = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      jmbg: this.state.jmbg,
      address: this.state.address,
      email: this.state.email,
      password: this.state.password,
      gender: this.state.gender,
    };
    console.log(data.gender);
    console.log(this.state.gender);

    var DD = data.jmbg[0] + data.jmbg[1];
    var MM = data.jmbg[2] + data.jmbg[3];
    var GGG = data.jmbg[4] + data.jmbg[5] + data.jmbg[6];

    if (data.firstName.length < 1) {
      alert("Invalid first name");
      return;
    } else if (data.lastName.length < 1) {
      alert("Invalid last name");
      return;
    } else if (data.jmbg.length !== 13) {
      alert("JMBG must have 13 digits");
      return;
    } else if (data.address.length < 1) {
      alert("Invalid address");
      return;
    } else if (data.jmbg.length !== 13) {
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
    } else if (!this.validateEmail(data.email)) {
      alert("Email is not valid! ");
      return;
    } else {
      if (this.state.creating) {
        if (data.gender === undefined) {
          data.gender = "MALE";
        }
        if (data.password.length < 6) {
          alert("Password too short!");
          return;
        }
        console.log(data.gender);
        axios
          .post("/nurses/add", data)
          .then((response) => {
            window.history.go(-1);
          })
          .catch((err) => {
            alert(err);
          });
      } else {
        axios
          .put("/nurses/update", data)
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
    return (
      <form className={classes.form} onSubmit={this.submitHandler}>
        <Row>
          <Col lg={6} md={6} sm={6} xs={6}>
            <label>First name</label>
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <input
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
              type="text"
              name="jmbg"
              value={this.state.jmbg}
              onChange={(event) => this.setState({ jmbg: event.target.value })}
            />
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <label>Address</label>
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <input
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
              type="text"
              name="Email"
              value={this.state.email}
              onChange={(event) => this.setState({ email: event.target.value })}
            />
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <label id="lblPassword">Password</label>
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <input
              id="password"
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
            <button className="btn btn-secondary" onClick={this.cancelHandler}>
              Cancel
            </button>
          </Col>
          <Col lg={6} md={6} sm={6} xs={6}>
            <input type="submit" value="submit" className="btn btn-success" />
          </Col>
          <Col lg={6} md={6} sm={6} xs={6} className={classes.block} id="del">
            <button
              disabled={this.props.creating}
              className="btn btn-danger"
              onClick={this.blockHandler}
            >
              Block nurse
            </button>
          </Col>
          {this.state.showPasswordChangeBtn && <Col lg={6} md={6} sm={6} xs={6} className="text-center" id="change">
            <Link to="/password-update">
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
    );
  }
}

export default withRouter(CreateUpdateNurse);