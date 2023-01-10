import React, { Component } from "react";
import classes from "./Doctors.module.css";
import axios from "axios";
import Doctor from "./Doctor/Doctor";
import {Link, withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTimes} from "@fortawesome/free-solid-svg-icons";
import authService from "../../services/auth-service";

class Doctors extends Component {
    state = {
        doctors: [],
        otherDoctors: [],
        showPopup: false
    }
    
    componentDidMount = () => {
        const clinicId = this.props.match.params.id;
        if(clinicId) {
            axios
                .get(`/clinics/${this.props.match.params.id}/doctors`)
                .then((response) => {
                    this.setState({ doctors: response.data });
                })
                .catch((error) => console.log(error));
        } else {
            axios
                .get("/doctors")
                .then((response) => {
                    this.setState({ doctors: response.data });
                })
                .catch((error) => console.log(error));
        }
    };
    
    showOtherDoctorsHandler = () => {
        axios
            .get("/doctors/not/clinic/" + this.props.match.params.id)
            .then((response) => {
                console.log(response.data)
                this.setState({
                    otherDoctors: response.data,
                    showPopup: true
                });
            })
            .catch(error => console.error(error));
    }
    
    addDoctorHandler = id => {
        let doctor = this.state.otherDoctors.find(doctor => {
            console.log(doctor);
            return doctor.id === id
        });
        doctor = { ...doctor, clinicId: Number(this.props.match.params.id)};
        
        axios
            .put("/doctors/update", doctor)
            .then((response) => {
                this.setState( {
                    doctors: [...this.state.doctors, doctor],
                    showPopup: false
                });
            })
            .catch(error => console.error(error));
    }
    
    render() {
        let doctors = <h2 className={["text-center", "my-5"].join(" ")}>There are no doctors</h2>;
        if (this.state.doctors.length > 0) {
            doctors = this.state.doctors.map(doctor => {
                return (
                    <Doctor
                        key={doctor.jmbg}
                        doctor={doctor}/>
                );
            });
        }
    
        let showPopup = null;
        if(this.state.showPopup) {
            let otherDoctors = <h5>Every doctor is in this clinic already.</h5>;
            if(this.state.otherDoctors.length > 0)
                otherDoctors = this.state.otherDoctors.map(doctor => <Doctor key={doctor.id} doctor={doctor}  btnClick={this.addDoctorHandler} choose/>);
    
            showPopup =
                <>
                    <div className={classes.backgroundOverlay} onClick={() => this.setState({ showPopup: false })}></div>
                    <div className={classes.addAdminPopup}>
                        <FontAwesomeIcon icon={faTimes} size="1x" onClick={() => this.setState({ showPopup: false })} className={classes.closePopupBtn}/>
                        <h3>Choose doctors</h3>
                        {otherDoctors}
                        {authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" && <Link to="/doctors/create" className={classes.addAdminBtn}>
                            <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginTop: "1.4rem", marginRight: "5px"}} />
                            <span>Add new doctor</span>
                        </Link>}
                    </div>
                </>
        }
        
        return (
            <>
                <h1 className={["text-center", "my-5", classes.title].join(" ")}>Doctors</h1>
                {doctors}
                {(!this.props.match.params.id && authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR") && <div className="d-flex justify-content-center">
                    <Link to="/doctors/create" className={classes.addAdminBtn}>
                        <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
                        <span>Add new doctor</span>
                    </Link>
                </div>}
                {(this.props.match.params.id && (authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" || authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR")) && <div className="d-flex justify-content-center">
                    <button className={classes.addAdminBtn} onClick={this.showOtherDoctorsHandler}>Add doctor</button>
                </div>}
                {showPopup}
            </>
        )
    }
}

export default withRouter(Doctors);