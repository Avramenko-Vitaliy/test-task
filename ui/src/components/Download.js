import React from 'react';
import PropTypes from 'prop-types';

const Download = props => (
    <div className="form-group">
        <div className="col-md-3">
            <button className="btn btn-info" onClick={props.onClickDownloadExchangeRate}>
                Download exchange rates
            </button>
        </div>
        <div className="col-md-3">
            <button className="btn btn-success" onClick={props.onClickDownloadExcel}>
                Download Excel
            </button>
        </div>
    </div>
);

Download.propTypes = {
    onClickDownloadExchangeRate: PropTypes.func.isRequired,
    onClickDownloadExcel: PropTypes.func.isRequired
};

export default Download;
