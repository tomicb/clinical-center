import React, { Component } from "react";
import { Calendar, momentLocalizer } from 'react-big-calendar'
import moment from 'moment'
import "react-big-calendar/lib/css/react-big-calendar.css";
import "./WorkCalendar.css"
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt, faClock, faMoneyBillWave, faUserNurse, faUserInjured } from "@fortawesome/free-solid-svg-icons";
import { Button } from "react-bootstrap";
import { Link, withRouter } from "react-router-dom";
import authService from "../../services/auth-service";

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
      price: 0,
      discount: 0,
      doctor: "",
      patient: "",
    },
  };

  componentDidMount() {
    if (this.props.match.params.jmbg) {
      axios
        .get(
          "/examinations/doctor/" +
            this.props.match.params.jmbg
        )
        .then((response) => {
          const events = response.data.map((examination) => ({
            ...examination,
            start: new Date(examination.start),
            end: new Date(examination.end),
          }));
          this.setState({ events: events });
        })
        .catch((error) => console.log(error));
    } else {
      axios
        .get("/examinations/doctorsExaminations")
        .then((response) => {
          const events = response.data.map((examination) => ({
            ...examination,
            start: new Date(examination.start),
            end: new Date(examination.end),
          }));
          this.setState({ events: events });
        })
        .catch((error) => console.log(error));
    }
  }

  selectedEventHandler = (event, e) => {
    console.log(event);
    console.log(e);
    this.setState({
      selectedExamination: {
        id: event.id,
        top: e.pageY,
        left: e.pageX,
        start: event.start,
        end: event.end,
        title: event.title,
        price: event.price,
        discount: event.discount,
        doctor: event.doctor,
        patient: event.patient,
      },
      showDetails: true,
    });
    console.log(this.state.events);
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
        price: 0,
        discount: 0,
        doctor: "",
        patient: "",
      },
      showDetails: false,
    });
  };

  selectedSlotHandler = ({ start, end }) => {
    const events = [...this.state.events];
    console.log(events);
    this.setState({
      events: [
        ...events,
        {
          start,
          end,
          title: "Available examination",
        },
      ],
    });
    console.log(start +",   " + end);
    let createdExamination = {
      start: start,
      end: end,
      title: "Available examination",
      doctorJmbg: this.props.match.params.jmbg,
    };
    
  axios.post("/examinations", createdExamination)
    .then(response =>{
        alert("Examination created successfully")
    })
    .catch(err => {
      alert("Can't create appointment in past!");
    });
  };

  render() {
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
            { this.state.selectedExamination.patient && <>
              <FontAwesomeIcon icon={faMoneyBillWave} size="1x" />{" "}
              <p>{this.state.selectedExamination.price} RSD</p>
              <br />
            </>}
            <FontAwesomeIcon icon={faUserNurse} size="1x" />{" "}
            <p>
              Dr. {this.state.selectedExamination.doctor.firstName}{" "}
              {this.state.selectedExamination.doctor.lastName}
            </p>
            <br />
            {((this.state.selectedExamination.patient && authService.getCurrentRole() !== "ROLE_PATIENT") || (authService.getCurrentRole() === "ROLE_PATIENT" && authService.getEmailFromJwt() === this.state.selectedExamination.patient.email)) && <>
              <FontAwesomeIcon icon={faUserInjured} size="1x" />{" "}
              <p>
                {this.state.selectedExamination.patient.firstName}{" "}
                {this.state.selectedExamination.patient.lastName}
              </p>
              <br />
              <Link
                to={`/examinations/${this.state.selectedExamination.id}`}
                key={this.state.selectedExamination.id}
              >
                <Button size="md" className="btn-start-appointment">
                  View appointment
                </Button>
              </Link>
            </>}
          </div>
        </>
      );
    }

    return (
      <div style={{ height: 800, width: "100%", marginBottom: "150px" }}>
        <h1 className={["text-center", "my-5"].join(" ")}>Work Calendar</h1>
        <Calendar
          localizer={localizer}
          events={this.state.events}
          startAccessor="start"
          endAccessor="end"
          step="30"
          defaultView="week"
          min={new Date(2019, 1, 1, 7, 0)}
          max={new Date(2019, 1, 1, 21, 0)}
          selectable="true"
          onSelectEvent={this.selectedEventHandler}
          onSelectSlot={authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" ? this.selectedSlotHandler : null}
        />
        {detailsPopup}
      </div>
    );
  }
}

export default withRouter(WorkCalendar);