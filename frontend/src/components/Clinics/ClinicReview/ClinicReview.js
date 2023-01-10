import React, { Component } from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import ReactStars from "react-rating-stars-component";
import classes from './ClinicReview.module.css';

class ClinicReview extends Component {
  state = {
    clinicName: "",
    clinicId: null,
    patientJmbg: "2403982698510",
    rating: 0,
    
  };

  componentDidMount = () => {
    let clinicId = this.props.match.params.id;
    this.setState({ clinicId: clinicId });

    axios
      .get("/clinics/" + clinicId)
      .then((response) => {
        this.setState({
          clinicName: response.data.name,
        });
      })
      .catch((err) => {
        console.error(err);
      });
  };

  submitHandler = (e) => {
    e.preventDefault();
    let data = {
      clinicId: this.state.clinicId,
      patientJmbg: this.state.patientJmbg,
      rating: this.state.rating,
    };
    axios
      .post("/clinics/evaluate", data)
      .then((response) => {
        console.log(response);
        this.props.history.push("/clinic-reports");
      })
      .catch((err) => {
        alert(err);
      });
  };

  starsHandler = (newRating) => {
    this.setState({ rating: newRating });
  };

  render() {
    let stars = {
      size: 60,
      value: 0,
      edit: true,
      isHalf: false,
    };

    return (
      <form className={classes.container} onSubmit={this.submitHandler}>
        <h1 className={classes.title}>{this.state.clinicName} review</h1>
        <ReactStars
          {...stars}
          className={classes.stars}
          onChange={this.starsHandler}
        />
        <input type="submit" value="Submit" className="btn btn-success" />
      </form>
    );
  }
}

export default withRouter(ClinicReview);