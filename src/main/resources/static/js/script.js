// ============================================
// Konfirmasi sebelum menghapus data
// ============================================
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-delete').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            const ok = confirm('Apakah Anda yakin ingin menghapus data ini?');
            if (!ok) {
                e.preventDefault();
            }
        });
    });

    // Auto-dismiss alert setelah 4 detik
    document.querySelectorAll('.alert').forEach(function (alertEl) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alertEl);
            bsAlert.close();
        }, 4000);
    });

    // ============================================
    // Pagination sederhana untuk tabel (client-side)
    // ============================================
    document.querySelectorAll('.paginated-table').forEach(function (table) {
        const pageSize = parseInt(table.getAttribute('data-page-size') || '5', 10);
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr')).filter(function (r) {
            return !r.querySelector('td[colspan]');
        });
        const controls = table.closest('.card-body').querySelector('.pagination-controls');

        if (rows.length <= pageSize || !controls) {
            return;
        }

        const totalPages = Math.ceil(rows.length / pageSize);
        let currentPage = 1;

        function renderPage(page) {
            currentPage = page;
            rows.forEach(function (row, index) {
                const rowPage = Math.floor(index / pageSize) + 1;
                row.style.display = (rowPage === page) ? '' : 'none';
            });
            renderControls();
        }

        function renderControls() {
            controls.innerHTML = '';
            const ul = document.createElement('div');
            ul.className = 'btn-group';

            const prevBtn = document.createElement('button');
            prevBtn.className = 'btn btn-sm btn-outline-secondary';
            prevBtn.innerHTML = '&laquo;';
            prevBtn.disabled = currentPage === 1;
            prevBtn.addEventListener('click', function () { renderPage(currentPage - 1); });
            ul.appendChild(prevBtn);

            for (let i = 1; i <= totalPages; i++) {
                const btn = document.createElement('button');
                btn.className = 'btn btn-sm ' + (i === currentPage ? 'btn-primary' : 'btn-outline-secondary');
                btn.textContent = i;
                btn.addEventListener('click', function () { renderPage(i); });
                ul.appendChild(btn);
            }

            const nextBtn = document.createElement('button');
            nextBtn.className = 'btn btn-sm btn-outline-secondary';
            nextBtn.innerHTML = '&raquo;';
            nextBtn.disabled = currentPage === totalPages;
            nextBtn.addEventListener('click', function () { renderPage(currentPage + 1); });
            ul.appendChild(nextBtn);

            controls.appendChild(ul);
        }

        renderPage(1);
    });
});
