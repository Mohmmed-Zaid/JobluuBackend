package com.Cubix.Jobluu.utility;

public class Data {

    public static String getMessageBody(String email, String otp) {
        return """
    <!DOCTYPE html>
    <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Verify your Jobluu Account</title>
      <style>
        body {
          margin: 0;
          padding: 0;
          font-family: 'Segoe UI', Roboto, Arial, sans-serif;
          background-color: #f4f6f9;
          color: #333;
        }
        .container {
          max-width: 600px;
          margin: 40px auto;
          background: #ffffff;
          border-radius: 8px;
          border: 1px solid #e1e4e8;
          box-shadow: 0 4px 10px rgba(0,0,0,0.05);
          overflow: hidden;
        }
        .header {
          padding: 30px 40px 20px;
          border-bottom: 1px solid #f1f3f5;
        }
        .header h1 {
          color: #2c3e50;
          margin: 0;
          font-size: 26px;
        }
        .header p {
          margin: 5px 0 0;
          color: #6c757d;
          font-size: 14px;
        }
        .body {
          padding: 40px;
        }
        .body h2 {
          font-size: 22px;
          color: #2c3e50;
          margin-bottom: 20px;
        }
        .body p {
          font-size: 15px;
          color: #495057;
          line-height: 1.6;
        }
        .otp-box {
          background-color: #f1f3f5;
          border: 2px dashed #ced4da;
          padding: 20px;
          margin: 30px 0;
          text-align: center;
          border-radius: 8px;
          cursor: pointer;
        }
        .otp-box:hover {
          background-color: #e9ecef;
        }
        .otp-label {
          font-size: 14px;
          color: #6c757d;
          margin-bottom: 8px;
        }
        .otp-code {
          font-size: 32px;
          font-family: 'Courier New', monospace;
          font-weight: bold;
          letter-spacing: 4px;
          color: #212529;
        }
        .warning {
          background-color: #fff3cd;
          color: #856404;
          border: 1px solid #ffeeba;
          padding: 16px;
          border-radius: 6px;
          margin-bottom: 24px;
          font-size: 14px;
        }
        .footer {
          background-color: #f8f9fa;
          text-align: center;
          padding: 20px;
          font-size: 12px;
          color: #6c757d;
          border-top: 1px solid #dee2e6;
        }
        a {
          color: #007bff;
          text-decoration: none;
          font-weight: 500;
        }
      </style>
    </head>
    <body>
      <div class="container">
        <div class="header">
          <h1>Jobluu</h1>
          <p>Your Career Partner</p>
        </div>
        <div class="body">
          <h2>Account Verification</h2>
          <p>Hello <strong>%s</strong>,</p>
          <p>We received a request to verify your Jobluu account. Use the verification code below to proceed:</p>

          <!-- OTP Box -->
          <div class="otp-box" onclick="copyOTP()">
            <div class="otp-label">Verification Code</div>
            <div class="otp-code" id="otp-code">%s</div>
          </div>

          <div class="warning">
            <strong>Note:</strong> This code will expire in 10 minutes. Please don’t share it with anyone.
          </div>

          <p>If you didn't request this, please ignore this email or <a href="mailto:support@jobluu.com">contact support</a>.</p>

          <p style="margin-top: 40px;">Regards,<br/><strong>Cubix Team</strong></p>
        </div>
        <div class="footer">
          <p>This email was sent to %s</p>
          <p>Need help? <a href="mailto:support@jobluu.com">Contact Support</a></p>
          <p>© 2025 Jobluu. All rights reserved.</p>
        </div>
      </div>

      <script>
        function copyOTP() {
          const otp = document.getElementById('otp-code').innerText;
          navigator.clipboard.writeText(otp).then(() => {
            alert('OTP copied to clipboard!');
          });
        }
      </script>
    </body>
    </html>
    """.formatted(email, otp, email);
    }

}
