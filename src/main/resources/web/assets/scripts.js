let http, refresh, builder, refs, data, run;

http = {
	get: function (url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function () {
			if (xhr.readyState == XMLHttpRequest.DONE) {
				let status = xhr.status;

				if (status >= 200 && status < 400) {
					return callback(JSON.parse(xhr.responseText), null, xhr);
				} else if (status) {
					return callback(null, JSON.parse(xhr.responseText), xhr);
				}

				return callback(null, "failed", xhr);
			}
		}
		xhr.open("GET", url, true);
		xhr.send(null);
	},
	delete: function (url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function () {
			if (xhr.readyState == XMLHttpRequest.DONE) {
				let status = xhr.status;

				if (status >= 200 && status < 400) {
					return callback(JSON.parse(xhr.responseText), null, xhr);
				} else if (status) {
					return callback(null, JSON.parse(xhr.responseText), xhr);
				}

				return callback(null, "failed", xhr);
			}
		}
		xhr.open("DELETE", url, true);
		xhr.send(null);
	},
	upload: function (url, file, callback, onProgress) {
		var formData = new FormData();
		formData.set('file', file);
		console.log(file)

		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function () {
			if (xhr.readyState == XMLHttpRequest.DONE) {
				let status = xhr.status;

				if (status >= 200 && status < 400) {
					return callback(JSON.parse(xhr.responseText), null, xhr);
				} else if (status) {
					return callback(null, JSON.parse(xhr.responseText), xhr);
				}

				return callback(null, "failed", xhr);
			}
		}
		if (onProgress)
			xhr.upload.addEventListener("progress", onProgress);
		xhr.open("POST", url, true);
		xhr.send(formData);
	}
}

refresh = {
	loading: '<div class="lds-ring"><div></div><div></div><div></div><div></div></div>',
	into(element) {
		element.innerHTML = refresh.loading;
	}
}

builder = {
	ref: function (ref) {
		return `
			<div class="chip">
				<a target="_blank" href="/api/refs/${ref}">${ref}</a>
				<span class="close" onclick="app.refs.delete('${ref}')">&times;</span>
			</div>
		`;
	},
	refs: function (refs) {
		return refs.map(builder.ref).join("");
	}
}

refs = {
	elements: {
		fileForm: document.getElementById("file-upload-form"),
		fileInput: document.getElementById("file-input"),
		fileSubmit: document.getElementById("file-submit"),
		fileList: document.getElementById("file-list"),
		listingPanel: document.getElementById("listing-panel"),
	},
	updateFileList() {
		refs.elements.fileList.innerHTML = '';
		data.files.forEach(function ({ file }, index) {
			let div = document.createElement('div');
			div.classList.add("file-item");

			let nameElement = document.createElement('p');
			nameElement.classList.add("file-name");
			nameElement.innerText = file.name;

			let progress = document.createElement('progress');
			progress.max = file.size;
			progress.value = 0;

			let statusElement = document.createElement('p');
			statusElement.classList.add("file-status");

			div.appendChild(nameElement);
			div.appendChild(progress);
			div.appendChild(statusElement);

			refs.elements.fileList.appendChild(div);

			data.files[index].progress = progress;
			data.files[index].statusElement = statusElement;
		});
	},
	updateListing(items) {
		refs.elements.listingPanel.innerHTML = builder.refs(items);
	},
	listing: {
		refresh() {
			refresh.into(refs.elements.listingPanel);
			http.get("http://localhost/api/refs", refs.updateListing)
		}
	},
	delete: function (ref) {
		if (confirm(`La référence ${ref} va être supprimé, confirmer?`)) {
			http.delete(`/api/refs/${ref}`, refs.listing.refresh);
		}
	}
}

data = {
	files: []
}

run = function () {
	refs.elements.fileForm.addEventListener('submit', function (event) {
		event.preventDefault();

		let done = 0;

		for (const { file, progress, statusElement } of data.files) {
			http.upload("http://localhost/api/refs", file, (response, error, request) => {
				progress.innerHTML = JSON.stringify(response);

				if (response?.success) {
					statusElement.innerText = "OK"
				} else {
					statusElement.innerText = "ERROR: " + (response?.error || error?.error);
				}

				if (++done == data.files.length) {
					refs.listing.refresh();
				}
			}, (progressEvent) => {
				progress.value = progressEvent.loaded;
				statusElement.innerText = `${((progressEvent.loaded / progressEvent.total) * 100).toFixed(1)}%`
			});
		}
	});

	refs.elements.fileInput.addEventListener('change', function (event) {
		data.files = [];
		for (var i = 0; i < refs.elements.fileInput.files.length; i++) {
			data.files.push({
				file: refs.elements.fileInput.files[i]
			})
		}
		refs.updateFileList();
	});

	refs.listing.refresh();
}

window.app = {
	http,
	refresh,
	builder,
	refs,
	run
};

window.app.run();