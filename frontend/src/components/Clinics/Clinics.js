import React, { Component } from "react";
import classes from "./Clinics.module.css";
import axios from "axios";
import Clinic from "./Clinic/Clinic";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus} from "@fortawesome/free-solid-svg-icons";
import authService from "../../services/auth-service";

class Clinics extends Component {
  state = {
    clinics: [],
    name: "",
    address: "",
    sort: "descending"
  };


  componentDidMount = () => {
    axios
      .get("/clinics")
      .then((response) => {
        console.log(response)
        this.setState({ clinics: response.data.clinics });
        
      })
      .catch((error) => console.log(error));
  };


  submitHandler = (e) => {
    e.preventDefault();
    let name = this.state.name;
    let address = this.state.address;
    let sort = this.state.sort;

    if(sort === "descending"){
      console.log(this.state.sort)
      axios.get(
        `/clinics/searched?name=${name}&address=${address}&sort=${sort}`
      )
      .then((response) => {
        this.setState({ clinics: response.data.clinics });
      })
      .catch((error) => console.log(error));
    }else{
      this.setState({sort: 'ascending'})
      console.log(this.state.sort)
      axios.get(
        `/clinics/searched?name=${name}&address=${address}&sort=${sort}`
      )
      .then((response) => {
        this.setState({ clinics: response.data.clinics });
      })
      .catch((error) => console.log(error));
    }
    

  };

  render() {
    let clinics = (
      <h2 style={{ textAlign: "center", marginTop: "30px", color: "#5d001e" }}>
        There are no clinics available!
      </h2>
    );

    if (this.state.clinics.length > 0) {
        clinics = this.state.clinics.map((clinic) => {
       
        return (
          <Clinic
            key={clinic.id}
            id={clinic.id}
            name={clinic.name}
            address={clinic.address}
            rating = {clinic.rating}
          />
        );
      });
    }

    return (
      <>
        <h1 className={["text-center", "my-5", classes.title].join(" ")}>
          Clinic search
        </h1>
        <form className={classes.form} onSubmit={this.submitHandler}>
          <input
            type="text"
            placeholder="Clinic name"
            name="name"
            value={this.state.name}
            onChange={(event) =>
              this.setState({ name: event.target.value })
            }
          />
          <input
            type="text"
            placeholder="Address"
            name="address"
            value={this.state.address}
            onChange={(event) => this.setState({ address: event.target.value })}
          />
          <select name="sort"
          onChange={(event) => this.setState({ sort: event.target.value })}>
            <option value="descending">Descending</option>
            <option value="ascending">Ascending</option>
          </select>
          <input
            type="submit"
            value="Submit"
            name="submit"
            className={["btn", "btn-success", classes.submit].join(" ")}
          />
        </form>
        {clinics}
        {authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR" && <div className="d-flex justify-content-center">
          <Link to="/clinics/create" className={classes.createClinicsBtn}>
            <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
            <span>Add a new clinic</span>
          </Link>
        </div>}
      </>
    );
  }
}

export default Clinics;
