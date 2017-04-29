import React from 'react';
import PropTypes from 'prop-types';

const Table = props => (
    <table className="table table-bordered">
        <thead>
        <tr>
            <th>Date</th>
            <th>Exchange rate</th>
        </tr>
        </thead>
        <tbody>
        {props.children}
        </tbody>
    </table>
);

Table.propTypes = {
    children: PropTypes.node
};

export default Table
