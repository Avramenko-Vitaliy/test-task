import React from 'react';
import PropTypes from 'prop-types';
import Datetime from 'react-datetime';

const DateRange = props => (
    <div className="form-group">
        <div className="col-sm-3">
            <label htmlFor="startDate">Date from</label>
            <Datetime
                closeOnSelect
                value={props.startDate}
                dateFormat="YYYY-MM-DD"
                timeFormat={false}
                inputProps={{ id: 'startDate', readOnly: true }}
                isValidDate={props.onValidateStartDate}
                onChange={props.onChangeStartDate}
            />
        </div>
        <div className="col-sm-3">
            <label htmlFor="endDate">Date to</label>
            <Datetime
                closeOnSelect
                value={props.endDate}
                dateFormat="YYYY-MM-DD"
                timeFormat={false}
                inputProps={{ id: 'endDate', readOnly: true }}
                isValidDate={props.onValidateEndDate}
                onChange={props.onChangeEndDate}
            />
        </div>
    </div>
);

DateRange.propTypes = {
    startDate: PropTypes.number,
    endDate: PropTypes.number,
    onValidateStartDate: PropTypes.func.isRequired,
    onValidateEndDate: PropTypes.func.isRequired,
    onChangeStartDate: PropTypes.func.isRequired,
    onChangeEndDate: PropTypes.func.isRequired
};

export default DateRange;
