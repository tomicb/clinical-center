import React, { Component } from "react";
import { Calendar, momentLocalizer } from 'react-big-calendar'
import moment from 'moment'
import "react-big-calendar/lib/css/react-big-calendar.css";
import "./ExaminationCalendar.module.css"
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt, faClock, faMoneyBillWave, faUserNurse, faUserInjured } from "@fortawesome/free-solid-svg-icons";
import { Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";

moment.locale("sr", {
    week: {
        dow: 1,
        doy: 1,
    }});

let localizer = momentLocalizer(moment);

class WorkCalendar extends Component {
  state = {
    events: [],
    showDetails: false,
    selectedExamination: {
      id: null,
      top: 0,
      left: 0,
      title: "",
      start: null,
      end: null,
      doctor: "",
      priceList: [],
      chosenExamination: "", 
    },
  };

  componentDidMount() {
      axios
        .get("examinations/available-examinations/" + this.props.match.params.id)
        .then((response) => {
          const events = response.data.map((examination) => ({
            ...examination,
            start: new Date(examination.start),
            end: new Date(examination.end),
          }));
          this.setState({ events: events });
          console.log(events);
        })
        .catch((error) => console.log(error));
  }

  selectedEventHandler = (event, e) => {
    this.setState({
      selectedExamination: {
        id: event.id,
        top: e.pageY,
        left: e.pageX,
        start: event.start,
        end: event.end,
        title: event.title,
        doctor: event.doctor,
        priceList: event.doctor.clinic.priceList,
        chosenExamination: event.doctor.clinic.priceList,
      },
      showDetails: true,
    });
  };

  removePopupHandler = () => {
    this.setState({
      selectedExamination: {
        id: null,
        top: 0,
        left: 0,
        title: "",
        start: null,
        end: null,
        doctor: "",
        priceList: [],
        chosenExamination: "",
      },
      showDetails: false,
    });
  };

  scheduleHandler = (e) =>{
    e.preventDefault();
    const data = {
      id: this.state.selectedExamination.id,
      title: this.state.chosenExamination
    };
    if(data.title == undefined){
      data.title = this.state.selectedExamination.priceList[0].name;
    }
    axios
    .put("/examinations/available-examination-schedule", data)
    .then(response =>{
      let events = [...this.state.events];
      events = events.filter(ev => ev.id !== this.state.selectedExamination.id);
      this.setState({events: events});
      this.removePopupHandler();
      alert("Check mail!");
    })
    .catch(err => alert(err));

  };


  render() {
    let options = <option value="">We dont have nothing to show.</option>;
    if(this.state.selectedExamination.priceList.length > 0){
      options = this.state.selectedExamination.priceList.map(option =>{

        return(
          <option key={option.id} value={option.name}>{option.name} ({option.price}$)</option>
        )
      })
    };

    let detailsPopup = null;
    if (this.state.showDetails) {
      detailsPopup = (
        <>
          <div
            className="popup-overlay"
            onClick={this.removePopupHandler}>
          </div>
          <div
            className="popup-content"
            style={{
              top: this.state.selectedExamination.top,
              left: this.state.selectedExamination.left,
            }}>
            <h4>{this.state.selectedExamination.title}</h4>
            <FontAwesomeIcon icon={faClock} size="1x" />{" "}
            <p>
              {moment(this.state.selectedExamination.start).format("HH:mm")} -{" "}
              {moment(this.state.selectedExamination.end).format("HH:mm")}
            </p>
            <br />
            <FontAwesomeIcon icon={faCalendarAlt} size="1x" />{" "}
            <p>
              {moment(this.state.selectedExamination.end).format(
                "DD. MMMM YYYY."
              )}
            </p>
            <br />
            <FontAwesomeIcon icon={faUserNurse} size="1x" />{" "}
            <p>
              Dr. {this.state.selectedExamination.doctor.firstName}{" "}
              {this.state.selectedExamination.doctor.lastName}
            </p>
            <br />
            <p>Choose examination:</p>
            <select
              name="chosenExamination"
              value={this.state.chosenExamination}
              onChange={(event) =>
                this.setState({ chosenExamination: event.target.value
                   })
              }
            >
              {options}
            </select>
            <br/>          
            <Button  onClick={this.scheduleHandler} size="md" className="btn-start-appointment">
              Schedule!
            </Button>
          </div>
        </>
      );
    }

    return (
      <div style={{ height: 800, width: "100%", marginBottom: "150px" }}>
        <h1 className={["text-center", "my-5"].join(" ")}>Available examination calendar</h1>
        <Calendar
          localizer={localizer}
          events={this.state.events}
          startAccessor="start"
          endAccessor="end"
          step="30"
          defaultView="week"
          min={new Date(2019, 1, 1, 7, 0)}
          max={new Date(2022, 1, 1, 21, 0)}
          selectable="true"
          onSelectEvent={this.selectedEventHandler}
        />
        {detailsPopup}
      </div>
    );
  }
}

export default withRouter(WorkCalendar);
