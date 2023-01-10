import React, { Component } from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import ReactStars from "react-rating-stars-component";
import classes from "./DoctorsReview.module.css";

class DoctorsReview extends Component {
  state = {
    doctorJmbg: "",
    doctorName: "",
    rating: 0,
  };

  componentDidMount = () => {
    let doctorJmbg = this.props.match.params.jmbg;
    this.setState({ doctorJmbg: doctorJmbg });

    axios
      .get("/doctors/" + doctorJmbg)
      .then((response) => {
        this.setState({
          doctorName: response.data.firstName + " " + response.data.lastName,
        });
      })
      .catch((err) => {
        console.error(err);
      });
  };

  submitHandler = (e) => {
    e.preventDefault();
    let data = {
      doctorJmbg: this.state.doctorJmbg,
      rating: this.state.rating,
    };
    axios
      .post("/doctors/evaluate", data)
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
        <h1 className={classes.title}>Dr. {this.state.doctorName} review</h1>
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

export default withRouter(DoctorsReview);
