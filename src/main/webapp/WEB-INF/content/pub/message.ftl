    <div class="modal fade" id="messageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo">${message!''}</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="modal fade" id="confirmMessageModal">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">提示信息</h4>
	      </div>
	      <div class="modal-body text-center">
	        <p id="showAlertInfo1"></p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal">取&nbsp;&nbsp;消</button>
	        <button id="confirmokBut" type="button" class="btn btn-primary btn-sm zd-btn-pd1" data-dismiss="modal" onclick="confirm()">确&nbsp;&nbsp;定</button>
	      </div>
	    </div>
	  </div>
	</div>