import React, { Component } from "react";
import _ from "lodash";
import ExaminationUser from "./ExaminationUser/ExaminationUser";
import ExaminationInfo from "./ExaminationInfo/ExaminationInfo"
import classes from './Examinaton.module.css';
import { Col, Row } from "react-bootstrap";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes, faPlus } from '@fortawesome/free-solid-svg-icons';
import { withRouter } from "react-router";
import authService from "../../services/auth-service";

class Examination extends Component {
    
    state = {
        examination: {
            id: null,
            doctor: null,
            patient: null,
            start: null,
            end: null,
            price: 0,
            discount: 0,
            report: "",
            recipe: null,
            title: ""
        },
        permission: false,
        loadedExamination: null,
        addMedicineTitle: "",
        loaded: false
    }
    
    componentDidMount() {
        axios
            .get("/examinations/" + this.props.match.params.id)
            .then((response) => {
                console.log(response.data)
                this.setState({
                    examination: {
                        id: response.data.id,
                        doctor: response.data.doctor,
                        patient: response.data.patient,
                        start: response.data.start,
                        end: response.data.end,
                        price: response.data.price,
                        discount: response.data.discount,
                        report: response.data.report,
                        recipe: response.data.recipe,
                        title: response.data.title
                    },
                    loadedExamination: {
                        id: response.data.id,
                        doctor: response.data.doctor,
                        patient: response.data.patient,
                        start: response.data.start,
                        end: response.data.end,
                        price: response.data.price,
                        discount: response.data.discount,
                        report: response.data.report,
                        recipe: response.data.recipe,
                        title: response.data.title
                    },
                    permission: response.data.permission,
                    loaded: true
                });
            })
            .catch((error) => console.log(error));
    }
    
    saveExaminationHandler = () => {
        let examination = this.state.examination;
        if(! _.isEqual(this.state.loadedExamination.recipe, this.state.examination.recipe)) {
            examination = {...examination, recipe: {...examination.recipe, status: "PENDING"}};
        }
        
        axios
            .put("/examinations/", examination)
            .then((response) => {
                this.props.history.push("/work-calendar");
            })
            .catch((error) => console.log(error));
    }
    
    addNewMedicine = () => {
        const newMedicine = {
            name: this.state.addMedicineTitle
        }
        let newMedication = [newMedicine];
        if(this.state.examination.recipe == null) {
            const newRecipe = {
                status: "PENDING",
                medication: newMedication,
                doctor: this.state.examination.doctor,
                patient: this.state.examination.patient,
                created: new Date(Date.now()),
                examination: this.state.examination,
                nurse: null
            }
            this.setState({examination: {...this.state.examination, recipe: newRecipe}, addMedicineTitle: ""})
        } else {
            newMedication = [...this.state.examination.recipe.medication, newMedicine];
            this.setState({examination: {...this.state.examination, recipe: {...this.state.examination.recipe, medication: newMedication}}, addMedicineTitle: ""})
        }
    }
    
    deleteMedicineHandler = index => {
        const medication = [...this.state.examination.recipe.medication];
        medication.splice(index, 1);
        this.setState({examination: {...this.state.examination, recipe: {...this.state.examination.recipe, medication: medication}}});
    }
    
    render() {
        if(this.state.loaded) {
            
            let medications = <h6 className={classes.noMedication}>There is no recipe prescribed!!</h6>;
            if(this.state.examination.recipe != null && this.state.examination.recipe.medication.length > 0) {
                medications = this.state.examination.recipe.medication.map((medication, index) => (
                    <div className={classes.medication} key={index}>
                        <p>{medication.name}</p>
                        {this.state.permission ?
                            <FontAwesomeIcon icon={faTimes} size="1x" className={classes.medicationIcon} onClick={() => this.deleteMedicineHandler(index)}/>
                            :null }
                    </div>
                ));
            }
            
            if(authService.getCurrentRole() === "ROLE_PATIENT" && authService.getEmailFromJwt() !== this.state.examination.patient.email) {
                return <>
                    <h1 className={["text-center", "my-5"].join(' ')}>{this.state.examination.title}</h1>
                    <h3 className={["text-center", "my-5"].join(' ')}>You dont have permission to view this examination!</h3>
                </>
            }
            
            return (
                <>
                    <h1 className={["text-center", "my-5"].join(' ')}>{this.state.examination.title}</h1>
                    <Row>
                        <Col lg={4} md={12} sm={12} xs={12}>
                            <ExaminationUser user={this.state.examination.patient}/>
                        </Col>
                        <Col lg={4} md={12} sm={12} xs={12}>
                            <ExaminationInfo start={this.state.examination.start} end={this.state.examination.end} price={this.state.examination.price} discount={this.state.examination.discount}/>
                        </Col>
                        <Col lg={4} md={12} sm={12} xs={12}>
                            <ExaminationUser user={this.state.examination.doctor} isDoctor/>
                        </Col>
                    </Row>
                    <div className={classes.report}>
                        <h4>Report</h4>
                        <textarea value={this.state.examination.report}
                            onChange={event => this.setState({ examination: { ...this.state.examination, report: event.target.value }})}
                            disabled={!this.state.permission} />
                    </div>
                    <div className={classes.medications}>
                        <h4>Recipe {this.state.examination.recipe !== null && this.state.examination.recipe.status === "PENDING" ? "- Waiting to be certified" : ``}</h4>
                        {medications}
                    </div>
                    <br/>
                    { this.state.permission ?
                        <>
                            <div className={["mt-1", classes.addMedication].join(" ")}>
                                <input type="text" value={this.state.addMedicineTitle} onChange={event => this.setState({ addMedicineTitle: event.target.value })}/>
                                <button onClick={this.addNewMedicine}>
                                    <FontAwesomeIcon icon={faPlus} size="1x"/>
                                </button>
                            </div>
                            <div className="text-center mt-5 mb-5">
                                <button className={["btn", "btn-lg", classes.btnSave].join(" ")} onClick={this.saveExaminationHandler} disabled={_.isEqual(this.state.loadedExamination, this.state.examination)}>Save examination</button>
                            </div>
                        </>: null}
                </>
            );
        }
        return null;
    }
}

export default withRouter(Examination);