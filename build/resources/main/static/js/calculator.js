function handleButton(button) {
    console.log('Button clicked:', button);
    
    // 送信前の状態をログに記録
    const currentDisplay = document.querySelector('.display-value');
    if (currentDisplay) {
        console.log('Current display before submit:', currentDisplay.textContent);
    }
    
    // AJAXを使用してボタンクリックを送信
    const formData = new FormData();
    formData.append('button', button);
    
    fetch('/button-ajax', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // 成功時はディスプレイを更新
            const display = document.querySelector('.display-value');
            if (display) {
                display.textContent = data.displayValue;
                console.log('Display updated to:', data.displayValue);
            }
            
            // 成功メッセージを表示
            showMessage(data.message, 'success');
        } else {
            console.error('Button click failed:', data.error);
            showMessage('エラー: ' + data.error, 'error');
        }
    })
    .catch(error => {
        console.error('Button click error:', error);
        // フォールバック: 従来のフォーム送信
        submitForm(button);
    });
}

function submitForm(button) {
    // フォームを作成してボタンクリックを送信（フォールバック用）
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/button';
    form.style.display = 'none';
    
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'button';
    input.value = button;
    
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}

function showMessage(message, type) {
    // 既存のメッセージを削除
    const existingAlert = document.querySelector('.alert');
    if (existingAlert) {
        existingAlert.remove();
    }
    
    // 新しいメッセージを作成
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    
    // メッセージを表示
    const container = document.querySelector('.container');
    if (container) {
        container.insertBefore(alertDiv, container.firstChild);
        
        // 3秒後にメッセージを自動削除
        setTimeout(() => {
            if (alertDiv.parentNode) {
                alertDiv.remove();
            }
        }, 3000);
    }
}

// キーボードイベントの処理
document.addEventListener('keydown', function(event) {
    const key = event.key;
    
    // 数字キー
    if (key >= '0' && key <= '9') {
        handleButton(key);
        event.preventDefault();
    }
    
    // 演算子キー
    switch (key) {
        case '+':
            handleButton('+');
            event.preventDefault();
            break;
        case '-':
            handleButton('-');
            event.preventDefault();
            break;
        case '*':
            handleButton('*');
            event.preventDefault();
            break;
        case '/':
            handleButton('/');
            event.preventDefault();
            break;
        case 'Enter':
        case '=':
            handleButton('=');
            event.preventDefault();
            break;
        case 'Escape':
            handleButton('C');
            event.preventDefault();
            break;
        case '.':
            handleButton('.');
            event.preventDefault();
            break;
        case '%':
            handleButton('%');
            event.preventDefault();
            break;
    }
});
