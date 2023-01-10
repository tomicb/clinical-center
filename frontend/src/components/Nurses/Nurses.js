import React, { Component } from "react";
import classes from "./Nurses.module.css";
import axios from "axios";
import Nurse from "./Nurse/Nurse";
import {Link, withRouter} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPlus, faTimes} from "@fortawesome/free-solid-svg-icons";
import authService from "../../services/auth-service";

class Nurses extends Component {
    state = {
        nurses: [],
        otherNurses: [],
        showPopup: false
    };
    
    componentDidMount = () => {
        const clinicId = this.props.match.params.id;
        if(clinicId) {
            axios
                .get(`/clinics/${this.props.match.params.id}/nurses`)
                .then((response) => {
                    this.setState({nurses: response.data});
                })
                .catch((error) => console.log(error));
        } else {
            axios
                .get("/nurses")
                .then((response) => {
                    this.setState({nurses: response.data});
                })
                .catch((error) => console.log(error));
        }
    };
    
    showOtherNursesHandler = () => {
        axios
            .get("/nurses/not/clinic/" + this.props.match.params.id)
            .then((response) => {
                console.log(response.data)
                this.setState({
                    otherNurses: response.data,
                    showPopup: true
                });
            })
            .catch(error => console.error(error));
    }
    
    addNurseHandler = id => {
        let nurse = this.state.otherNurses.find(nurse => nurse.id === id);
        nurse = { ...nurse, clinicId: Number(this.props.match.params.id)};
        
        axios
            .put("/nurses/update", nurse)
            .then((response) => {
                this.setState( {
                    nurses: [...this.state.nurses, nurse],
                    showPopup: false
                });
            })
            .catch(error => console.error(error));
    }
    
    render() {
        let nurses = <h2 className={["text-center", "my-5"].join(" ")}>There are no nurses</h2>;
        if (this.state.nurses.length > 0) {
            nurses = this.state.nurses.map(nurse => {
                return (
                    <Nurse
                        key={nurse.jmbg}
                        nurse={nurse}/>
                );
            });
        }
    
        let showPopup = null;
        if(this.state.showPopup) {
            let otherNurses = <h5>Every nurse is in this clinic already.</h5>;
            if(this.state.otherNurses.length > 0)
                otherNurses = this.state.otherNurses.map(nurse => <Nurse nurse={nurse}  btnClick={this.addNurseHandler} choose/>);
    
            showPopup =
                <>
                    <div className={classes.backgroundOverlay} onClick={() => this.setState({ showPopup: false })}></div>
                    <div className={classes.addAdminPopup}>
                        <FontAwesomeIcon icon={faTimes} size="1x" onClick={() => this.setState({ showPopup: false })} className={classes.closePopupBtn}/>
                        <h3>Choose nurse</h3>
                        {otherNurses}
                        {authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" && <Link to="/nurses/create" className={classes.addAdminBtn}>
                            <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginTop: "1.4rem", marginRight: "5px"}} />
                            <span>Add new nurse</span>
                        </Link>}
                    </div>
                </>
        }
        
        return (
            <>
                <h1 className={["text-center", "my-5", classes.title].join(" ")}>Nurses</h1>
                {nurses}
                {(!this.props.match.params.id && authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR") && <div className="d-flex justify-content-center">
                    <Link to="/nurses/create" className={classes.addAdminBtn}>
                        <FontAwesomeIcon icon={faPlus} style={{fontSize: "1rem", marginRight: "5px"}} />
                        <span>Add new nurse</span>
                    </Link>
                </div>}
                {(this.props.match.params.id && (authService.getCurrentRole() === "ROLE_CLINIC_ADMINISTRATOR" || authService.getCurrentRole() === "ROLE_CLINIC_CENTRE_ADMINISTRATOR")) && <div className="d-flex justify-content-center">
                    <button className={classes.addAdminBtn} onClick={this.showOtherNursesHandler}>Add nurse</button>
                </div>}
                {showPopup}
            </>
        );
    }
}

export default withRouter(Nurses);