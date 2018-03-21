<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Ports Finder Tool</span></div>
		<div class="panel-body">
	        <div class="formcontainer">
	            <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
	            <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
	            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
	                <div class="row">
	                    <div class="form-group col-md-12">
	                        <label class="col-md-3 control-lable" for="host">Enter Host Name or I.P Address</label>
	                        <div class="col-md-7">
	                            <input type="text" ng-model="ctrl.host" id="host" class="portname form-control input-sm" placeholder="Host Name or I.P Address" required />
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="row">
	                    <div class="form-actions floatRight">
	                        <input type="submit"  value="Find" class="btn btn-primary btn-sm">
	                    </div>
	                </div>
	            </form>
    	    </div>
		</div>	
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of ports</span></div>
		<div class="panel-body">
			<div class="table-responsive">
		        <table class="table table-hover">
		            <thead>
		            <tr>
		                <th>PORT</th>
		                <th>TIME OF SCAN</th>
		                <th>SERVICE PROVIDED</th>
		                <th>STATE</th>
		                <th>ANY CHANGE IN STATE</th>		                		               		               
		            </tr>
		            </thead>
		            <tbody>
		            <tr ng-repeat="p in ctrl.ports">
		                <td>{{p.portNumber}}</td>
		                <td>{{p.scanTime}}</td>
		                <td>{{p.serviceUsedFor}}</td>		                
		                <td bgcolor="{{p.portState != 'OPEN' ? '#F27F7F' : '#d6e9c6'}}">{{p.portState}}</td>
		                <td bgcolor="{{p.isStateChanged == 'YES'  ? (p.portState != 'OPEN' ? '#d75466' : '#17A2B8') : ''}}">{{p.isStateChanged}}</td>	                		                
		            </tr>
		            </tbody>
		        </table>		
			</div>
		</div>
    </div>
</div>